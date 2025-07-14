package com.couponcenter.coupon.impl.converter;

import com.couponcenter.couponcenter.couponcenter.couponcenter.coupon.api.beans.CouponTemplateInfo;
import com.couponcenter.couponcenter.couponcenter.couponcenter.coupon.dao.entity.CouponTemplateEntity;

public class CouponTemplateConverter {
    public static CouponTemplateInfo convertToTemplateInfo(CouponTemplateEntity template) {

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
