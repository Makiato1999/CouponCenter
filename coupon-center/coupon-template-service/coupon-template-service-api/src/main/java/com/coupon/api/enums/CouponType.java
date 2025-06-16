package com.coupon.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CouponType {

    UNKNOWN("unknown", "0"),
    MONEY_OFF("满减券", "1"),
    DISCOUNT("打折", "2"),
    RANDOM_DISCOUNT("随机减", "3"),
    LONELY_NIGHT_MONEY_OFF("寂寞午夜double券", "4"),
    ANTI_PUA("PUA加倍奉还券", "5");

    private String description;

    // 存在数据库里的最终code
    private String code;

    public static CouponType convert(String code) {
        return Stream.of(values())
                // 当你定义一个 enum 类时，Java 编译器会自动为这个枚举类添加一个 values() 方法，
                // 它返回该枚举类型中所有常量的数组
                .filter(couponType -> couponType.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
