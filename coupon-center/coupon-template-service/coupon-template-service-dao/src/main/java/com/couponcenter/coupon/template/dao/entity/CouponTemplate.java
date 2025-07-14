package com.couponcenter.coupon.template.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.couponcenter.coupon.template.api.beans.rules.TemplateRule;
import com.couponcenter.coupon.template.api.enums.CouponType;
import com.couponcenter.coupon.template.dao.converter.CouponTypeConverter;
import com.couponcenter.coupon.template.dao.converter.RuleConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券模板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon_template")
public class CouponTemplate implements Serializable {
    /**
     * 这里我要补充一点，其实JPA也支持一对多、多对多的级联关系（ManyToOne、OneToOne等注解），
     * 但是你发现我并没有在项目中使用，原因是这些注解背后有很多隐患。
     * **过深的级联层级所带来的DB层压力可能会在洪峰流量下被急剧放大，
     * 而DB恰恰是最不抗压的一环。**
     * 所以，我们很少在一些一二线大厂的超高并发项目中看到级联配置的身影。
     *
     * 尽可能减少级联配置，用单表查询取而代之，
     * 如果一个查询需要join好几张表，最好的做法就通过重构业务逻辑来简化DB查询的复杂度。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 状态是否可用
    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "name", nullable = false)
    private String name;

    // 适用门店-如果为空，则为全店满减券
    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "description", nullable = false)
    private String description;

    // 优惠券类型
    @Column(name = "type", nullable = false)
    @Convert(converter = CouponTypeConverter.class)
    private CouponType category;

    // 创建时间，通过@CreateDate注解自动填值（需要配合@JpaAuditing注解在启动类上生效）
    @CreatedDate
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    // 优惠券核算规则，平铺成JSON字段
    @Column(name = "rule", nullable = false)
    @Convert(converter = RuleConverter.class)
    private TemplateRule rule;
} 