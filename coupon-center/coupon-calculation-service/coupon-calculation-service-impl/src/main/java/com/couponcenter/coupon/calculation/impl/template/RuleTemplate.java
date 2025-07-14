package com.couponcenter.coupon.calculation.impl.template;

import com.couponcenter.coupon.calculation.api.beans.ShoppingCart;

public interface RuleTemplate {

    // 计算优惠券
    ShoppingCart calculate(ShoppingCart settlement);
}
