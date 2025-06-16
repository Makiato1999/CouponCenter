package com.coupon.impl.controller;

import com.alibaba.fastjson.JSON;
import com.coupon.api.beans.CouponTemplateInfo;
import com.coupon.api.beans.PagedCouponTemplateInfo;
import com.coupon.api.beans.TemplateSearchParams;
import com.coupon.impl.service.intf.CouponTemplateService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/templates")
public class CouponTemplateController {
    /**
     * 资源路径（用 PathVariable）和查询条件（用 RequestParam）
     */

    @Resource
    private CouponTemplateService couponTemplateService;

    /**
     * 创建优惠券
     * POST /templates
     */
    @PostMapping("")
    public CouponTemplateInfo addTemplate(@Valid @RequestBody CouponTemplateInfo request) {
        log.info("Create coupon template: data={}", request);
        return couponTemplateService.createTemplate(request);
    }

    /**
     * 克隆优惠券模板
     * POST /templates/{id}/clone
     */
    @PostMapping("/{id}/clone")
    public CouponTemplateInfo cloneTemplate(@PathVariable("id") Long templateId) {
        log.info("Clone coupon template: data={}", templateId);
        return couponTemplateService.cloneTemplate(templateId);
    }

    /**
     * 获取指定模板,读取优惠券
     * GET /templates/{id}
     */
    @GetMapping("/{id}")
    public CouponTemplateInfo getTemplate(@PathVariable("id") Long id){
        log.info("Load template, id={}", id);
        return couponTemplateService.loadTemplateInfo(id);
    }

    /**
     * 批量获取多个模板
     * GET /templates/batch?ids=1,2,3
     */
    @GetMapping("/batch")
    public Map<Long, CouponTemplateInfo> getTemplateInBatch(@RequestParam("ids") Collection<Long> ids) {
        log.info("getTemplateInBatch: {}", JSON.toJSONString(ids));
        return couponTemplateService.getTemplateInfoMap(ids);
    }

    /**
     * 搜索模板（分页+条件）
     * POST /templates/search
     */
    @PostMapping("/search")
    public PagedCouponTemplateInfo search(@Valid @RequestBody TemplateSearchParams request) {
        log.info("search templates, payload={}", request);
        return couponTemplateService.search(request);
    }

    /**
     * 删除模板（逻辑删除或物理删除根据业务实现）优惠券无效化
     * DELETE /templates/{id}
     */
    @DeleteMapping("/{id}")
    public void deleteTemplate(@RequestParam("id") Long id){
        log.info("Load template, id={}", id);
        couponTemplateService.deleteTemplate(id);
    }
}
