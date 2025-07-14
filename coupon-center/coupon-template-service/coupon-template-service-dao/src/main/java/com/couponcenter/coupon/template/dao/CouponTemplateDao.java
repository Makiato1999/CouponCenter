package com.couponcenter.coupon.template.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.couponcenter.coupon.template.dao.entity.CouponTemplate;

import java.util.List;

@Repository
public interface CouponTemplateDao extends JpaRepository<CouponTemplate, Long> {
    // 根据Shop ID查询出所有券模板
    List<CouponTemplate> findAllByShopId(Long shopId);

    // IN查询 + 分页支持的语法
    Page<CouponTemplate> findAllByIdIn(List<Long> Id, Pageable page);

    // 根据shop ID + 可用状态查询店铺有多少券模板
    Integer countByShopIdAndAvailable(Long shopId, Boolean available);

    // 将优惠券设置为不可用
    @Modifying
    @Query("update CouponTemplate c set c.available = 0 where c.id = :id")
    int makeCouponUnavailable(@Param("id") Long id);

    /**
     * 以接口名查询的方式虽然很省事儿，但它面对复杂查询却力不从心，
     * 一来容易导致接口名称过长，二来维护起来也挺吃力的。
     * 所以，对于复杂查询，我们可以使用自定义SQL、或者Example对象查找的方式。
     */
}
