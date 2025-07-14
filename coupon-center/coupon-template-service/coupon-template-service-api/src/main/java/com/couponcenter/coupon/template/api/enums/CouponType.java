package com.couponcenter.coupon.template.api.enums;

import lombok.Getter;

@Getter
public enum CouponType {
    UNKNOWN("unknown", "0"),
    MONEY_OFF("满减券", "1"),
    DISCOUNT("打折", "2"),
    RANDOM_DISCOUNT("随机减", "3"),
    LONELY_NIGHT_MONEY_OFF("寂寞午夜double券", "4"),
    ANTI_PUA("PUA加倍奉还券", "5");

    private final String description;
    private final String code;

    CouponType(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public static CouponType convert(String code) {
        for (CouponType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
