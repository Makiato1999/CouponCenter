package com.couponcenter.coupon.template.impl.controller;

import com.alibaba.fastjson.JSON;
import com.couponcenter.coupon.template.api.beans.CouponTemplateInfo;
import com.couponcenter.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.couponcenter.coupon.template.api.beans.TemplateSearchParams;
import com.couponcenter.coupon.template.impl.service.intf.CouponTemplateService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

import org.springframework.core.env.Environment;

@Slf4j
@RestController
@RequestMapping("/template")
public class CouponTemplateController {

    @Autowired
    private CouponTemplateService couponTemplateService;

    @Autowired
    private Environment environment;

    // 创建优惠券
    @PostMapping("/addTemplate")
    public CouponTemplateInfo addTemplate(@Valid @RequestBody CouponTemplateInfo request) {
        log.info("Create coupon template: data={}", request);
        return couponTemplateService.createTemplate(request);
    }

    @PostMapping("/cloneTemplate")
    public CouponTemplateInfo cloneTemplate(@RequestParam("id") Long templateId) {
        log.info("Clone coupon template: data={}", templateId);
        return couponTemplateService.cloneTemplate(templateId);
    }

    // 读取优惠券
    @GetMapping("/getTemplate")
    public CouponTemplateInfo getTemplate(@RequestParam("id") Long id){
        // 获取本实例 metadata
        String trafficVersion = environment.getProperty("spring.cloud.nacos.discovery.metadata.traffic-version");
        int port = Integer.parseInt(environment.getProperty("server.port"));
        log.info("getTemplate called, id={}, port={}, trafficVersion={}", id, port, trafficVersion);
        
        log.info("Load template, id={}", id);
        return couponTemplateService.loadTemplateInfo(id);
    }

    // 批量获取
    @GetMapping("/getBatch")
    public Map<Long, CouponTemplateInfo> getTemplateInBatch(@RequestParam("ids") Collection<Long> ids) {
        log.info("getTemplateInBatch: {}", JSON.toJSONString(ids));
        return couponTemplateService.getTemplateInfoMap(ids);
    }

    // 搜索模板
    @PostMapping("/search")
    public PagedCouponTemplateInfo search(@Valid @RequestBody TemplateSearchParams request) {
        log.info("search templates, payload={}", request);
        return couponTemplateService.search(request);
    }

    // 优惠券无效化
    @DeleteMapping("/deleteTemplate")
    public void deleteTemplate(@RequestParam("id") Long id){
        log.info("Load template, id={}", id);
        couponTemplateService.deleteTemplate(id);
    }
}