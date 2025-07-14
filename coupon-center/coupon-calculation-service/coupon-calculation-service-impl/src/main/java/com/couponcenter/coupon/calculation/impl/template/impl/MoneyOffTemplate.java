package com.couponcenter.coupon.calculation.impl.template.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.couponcenter.coupon.calculation.impl.template.AbstractRuleTemplate;
import com.couponcenter.coupon.calculation.impl.template.RuleTemplate;

/**
 * 满减优惠券计算规则
 */
@Slf4j
@Component
public class MoneyOffTemplate extends AbstractRuleTemplate implements RuleTemplate {

    @Override
    protected Long calculateNewPrice(Long totalAmount, Long shopAmount, Long quota) {
        // benefitAmount是扣减的价格
        // 如果当前门店的商品总价<quota，那么最多只能扣减shopAmount的钱数
        Long benefitAmount = shopAmount < quota ? shopAmount : quota;
        return totalAmount - benefitAmount;
    }
}
