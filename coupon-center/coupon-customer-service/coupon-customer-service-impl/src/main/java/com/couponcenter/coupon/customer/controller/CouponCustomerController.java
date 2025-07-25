package com.couponcenter.coupon.customer.controller;

import com.couponcenter.coupon.calculation.api.beans.ShoppingCart;
import com.couponcenter.coupon.calculation.api.beans.SimulationOrder;
import com.couponcenter.coupon.calculation.api.beans.SimulationResponse;
import com.couponcenter.coupon.customer.api.beans.RequestCoupon;
import com.couponcenter.coupon.customer.api.beans.SearchCoupon;
import com.couponcenter.coupon.customer.dao.entity.Coupon;
import com.couponcenter.coupon.customer.service.intf.CouponCustomerService;
import com.couponcenter.coupon.template.api.beans.CouponInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("coupon-customer")
public class CouponCustomerController {

    @Autowired
    private CouponCustomerService customerService;

    @PostMapping("requestCoupon")
    public Coupon requestCoupon(@Valid @RequestBody RequestCoupon request,
    @RequestHeader(value = "traffic-version", required = false) String trafficVersion) {
        request.setTrafficVersion(trafficVersion);
        return customerService.requestCoupon(request);
    }

    // 用户删除优惠券
    @DeleteMapping("deleteCoupon")
    public void deleteCoupon(@RequestParam("userId") Long userId,
                                       @RequestParam("couponId") Long couponId) {
        customerService.deleteCoupon(userId, couponId);
    }

    // 用户模拟计算每个优惠券的优惠价格
    @PostMapping("simulateOrder")
    public SimulationResponse simulate(@Valid @RequestBody SimulationOrder order) {
        return customerService.simulateOrderPrice(order);
    }

    // ResponseEntity - 指定返回状态码 - 可以作为一个课后思考题
    @PostMapping("placeOrder")
    public ShoppingCart checkout(@Valid @RequestBody ShoppingCart info) {
        return customerService.placeOrder(info);
    }


    // 实现的时候最好封装一个search object类
    @PostMapping("findCoupon")
    public List<CouponInfo> findCoupon(@Valid @RequestBody SearchCoupon request) {
        return customerService.findCoupon(request);
    }

}
