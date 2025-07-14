package com.couponcenter.coupon.template.dao.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.couponcenter.coupon.template.api.enums.CouponType;

@Converter
public class CouponTypeConverter implements AttributeConverter<CouponType, String> {

    @Override
    public String convertToDatabaseColumn(CouponType couponCategory) {
        return couponCategory.getCode();
    }

    @Override
    public CouponType convertToEntityAttribute(String code) {
        return CouponType.convert(code);
    }
}
