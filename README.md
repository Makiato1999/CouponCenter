# CouponCenter
## 项目简介

CouponCenter 是一个分布式的优惠券中心系统，旨在为电商平台或其他需要优惠券服务的系统提供高效、灵活的优惠券发放、管理与结算能力。项目采用微服务架构，模块化设计，易于扩展和维护。

## 项目结构

- `coupon-calculation-service`  
  优惠券结算服务，负责优惠券的计算逻辑。
  - `coupon-calculation-service-api`  
    结算服务的接口模块，定义了对外暴露的API。
- `coupon-template-service`  
  优惠券模板服务，负责优惠券模板的创建与管理。
  - `coupon-template-service-api`  
    模板服务的接口模块。
- 其他相关服务...

## 技术栈

- Java 8
- Spring Boot
- Spring Cloud
- Maven
- Lombok
- JSR 380 (Bean Validation)

## 快速开始

1. **克隆项目**
   ```
   git clone https://github.com/your-org/CouponCenter.git
   ```

2. **构建项目**
   ```
   mvn clean install
   ```

3. **启动服务**
   进入各服务模块，使用如下命令启动：
   ```
   mvn spring-boot:run
   ```

4. **接口文档**
   各服务启动后可通过 `/swagger-ui.html` 访问接口文档。

## 主要功能

- 优惠券模板的创建、查询、管理
- 优惠券的发放、领取、核销
- 多种优惠券结算规则支持
- 支持分布式部署与扩展

## 贡献指南

欢迎提交 issue 和 PR！如有建议或问题请在 issue 区留言。

## License

本项目采用 MIT License。
