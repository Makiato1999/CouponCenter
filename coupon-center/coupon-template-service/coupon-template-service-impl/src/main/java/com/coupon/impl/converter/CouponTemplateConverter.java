package com.coupon.impl.converter;

import com.coupon.api.beans.CouponTemplateInfo;

public class CouponTemplateConverter {
    public static CouponTemplateInfo convertToTemplateInfo(com.coupon.dao.entity.CouponTemplate template) {

        return CouponTemplateInfo.builder()
                .id(template.getId())
                .name(template.getName())
                .desc(template.getDescription())
                .type(template.getCategory().getCode())
                .shopId(template.getShopId())
                .available(template.getAvailable())
                .rule(template.getRule())
                .build();
    }
}
