package com.couponcenter.coupon.customer.service.intf;

import com.couponcenter.coupon.calculation.api.beans.ShoppingCart;
import com.couponcenter.coupon.calculation.api.beans.SimulationOrder;
import com.couponcenter.coupon.calculation.api.beans.SimulationResponse;
import com.couponcenter.coupon.customer.api.beans.RequestCoupon;
import com.couponcenter.coupon.customer.api.beans.SearchCoupon;
import com.couponcenter.coupon.customer.dao.entity.Coupon;
import com.couponcenter.coupon.template.api.beans.CouponInfo;
import io.micrometer.core.instrument.search.Search;

import java.util.List;

// 用户对接服务
public interface CouponCustomerService {

    // 领券接口
    Coupon requestCoupon(RequestCoupon request);

    // 核销优惠券
    ShoppingCart placeOrder(ShoppingCart info);

    // 优惠券金额试算
    SimulationResponse simulateOrderPrice(SimulationOrder order);

    void deleteCoupon(Long userId, Long couponId);

    // 查询用户优惠券
    List<CouponInfo> findCoupon(SearchCoupon request);
}
