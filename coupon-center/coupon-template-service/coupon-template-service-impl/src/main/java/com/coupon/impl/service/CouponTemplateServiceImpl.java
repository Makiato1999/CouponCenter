package com.coupon.impl.service;

import com.coupon.api.beans.CouponTemplateInfo;
import com.coupon.api.beans.PagedCouponTemplateInfo;
import com.coupon.api.beans.TemplateSearchParams;
import com.coupon.api.enums.CouponType;
import com.coupon.dao.CouponTemplateDao;
import com.coupon.dao.entity.CouponTemplateEntity;
import com.coupon.impl.converter.CouponTemplateConverter;
import com.coupon.impl.service.intf.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CouponTemplateServiceImpl implements CouponTemplateService {

    @Resource
    private CouponTemplateDao templateDao;

    /**
     * 创建优惠券模板
     *
     * 从前端收到一个 CouponTemplateInfo 请求，
     * 做校验 → 转成实体类 CouponTemplate → 存入数据库 → 再把存好的实体转回 CouponTemplateInfo 返回前端
     */
    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo request) {
        // 单个门店最多可以创建100张优惠券模板
        if (request.getShopId() != null) {
            Integer count = templateDao.countByShopIdAndAvailable(request.getShopId(), true);
            if (count >= 100) {
                log.error("the totals of coupon template exceeds maximum number");
                throw new UnsupportedOperationException("exceeded the maximum of coupon templates that you can create");
            }
        }

        // 创建优惠券
        CouponTemplateEntity template = CouponTemplateEntity.builder()
                .name(request.getName())
                .description(request.getDesc())
                .category(CouponType.convert(request.getType()))
                .available(true)
                .shopId(request.getShopId())
                .rule(request.getRule())
                .build();

        template = templateDao.save(template);
        // Spring Data JPA 的保存方法，返回的是带主键 ID 的实体。

        return CouponTemplateConverter.convertToTemplateInfo(template);
    }

    /**
     * clone优惠券模板
     */
    @Override
    public CouponTemplateInfo cloneTemplate(Long templateId) {
        log.info("cloning template id {}", templateId);
        CouponTemplateEntity source = templateDao.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("invalid template ID"));

        CouponTemplateEntity target = new CouponTemplateEntity();
        BeanUtils.copyProperties(source, target);

        target.setAvailable(true);
        target.setId(null);

        templateDao.save(target);
        return CouponTemplateConverter.convertToTemplateInfo(target);
    }

    /**
     * 搜索
     *
     * 根据前端传来的搜索条件 TemplateSearchParams，构造查询条件，
     * 分页查找数据库中的 CouponTemplate 模板，最后返回分页结构 PagedCouponTemplateInfo。
     */
    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams request) {
        CouponTemplateEntity example = CouponTemplateEntity.builder()
                .shopId(request.getShopId())
                .category(CouponType.convert(request.getType()))
                .available(request.getAvailable())
                .name(request.getName())
                .build();

        // 构造分页参数对象
        Pageable page = PageRequest.of(request.getPage(), request.getPageSize());
        // 发起分页查询
        Page<CouponTemplateEntity> result = templateDao.findAll(Example.of(example), page);
        // 这是 JPA 的分页 + 条件查询，非常简洁：
        // 用 Example 做 where 条件
        // 用 Pageable 做分页
        // 返回的是 Page<CouponTemplate>，带分页元信息

        // 实体转 DTO 列表
        List<CouponTemplateInfo> couponTemplateInfos = result.stream()
                .map(CouponTemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toList());

        PagedCouponTemplateInfo response = PagedCouponTemplateInfo.builder()
                .templates(couponTemplateInfos)
                .page(request.getPage())
                .total(result.getTotalElements())
                .build();

        return response;
    }

    public List<CouponTemplateInfo> searchTemplate(CouponTemplateInfo request) {
        CouponTemplateEntity example = CouponTemplateEntity.builder()
                .shopId(request.getShopId())
                .category(CouponType.convert(request.getType()))
                .available(request.getAvailable())
                .name(request.getName())
                .build();
        // 可以用下面的方式做分页查询
//        Pageable page = PageRequest.of(0, 100);
//        templateDao.findAll(Example.of(example), page);
        List<CouponTemplateEntity> result = templateDao.findAll(Example.of(example));
        return result.stream()
                .map(CouponTemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toList());
    }

    /**
     * 通过ID查询优惠券模板
     */
    @Override
    public CouponTemplateInfo loadTemplateInfo(Long id) {
        Optional<CouponTemplateEntity> template = templateDao.findById(id);
        return template.isPresent() ? CouponTemplateConverter.convertToTemplateInfo(template.get()) : null;
    }

    /**
     * 将券无效化
     */
    @Override
    public void deleteTemplate(Long id) {
        int rows = templateDao.makeCouponUnavailable(id);
        if (rows == 0) {
            throw new IllegalArgumentException("Template Not Found: " + id);
        }
    }

    /**
     * 批量读取模板
     *
     * 根据一批优惠券模板 ID，批量从数据库查询这些模板，
     * 并返回 Map<模板ID, CouponTemplateInfo>，方便快速按 ID 查找模板信息。
     */
    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
//        List<CouponTemplateEntity> templates = templateDao.findAllById(ids);
//
//        return templates.stream()
//                .map(CouponTemplateConverter::convertToTemplateInfo)
//                .collect(Collectors.toMap(CouponTemplateInfo::getId, Function.identity()));
        List<CouponTemplateEntity> templates = templateDao.findAllById(ids);
        Map<Long, CouponTemplateInfo> result = new HashMap<>();

        for (CouponTemplateEntity entity : templates) {
            CouponTemplateInfo info = CouponTemplateConverter.convertToTemplateInfo(entity);
            result.put(info.getId(), info);
        }
        return result;
    }
}
