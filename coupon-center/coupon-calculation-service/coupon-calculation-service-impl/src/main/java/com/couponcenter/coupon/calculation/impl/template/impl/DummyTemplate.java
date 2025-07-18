package com.couponcenter.coupon.calculation.impl.template.impl;

import com.couponcenter.coupon.calculation.api.beans.ShoppingCart;
import com.couponcenter.coupon.calculation.impl.template.AbstractRuleTemplate;
import com.couponcenter.coupon.calculation.impl.template.RuleTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 空实现
 */
@Slf4j
@Component
public class DummyTemplate extends AbstractRuleTemplate implements RuleTemplate {

    @Override
    public ShoppingCart calculate(ShoppingCart order) {
        // 获取订单总价
        Long orderTotalAmount = getTotalPrice(order.getProducts());

        order.setCost(orderTotalAmount);
        return order;
    }


    @Override
    protected Long calculateNewPrice(Long orderTotalAmount, Long shopTotalAmount, Long quota) {
        return orderTotalAmount;
    }
}
