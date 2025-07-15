# CouponCenter

## 项目简介 | Project Overview

CouponCenter 是一个基于微服务架构的分布式优惠券中心系统，适用于电商平台等需要灵活优惠券管理与结算的场景。系统支持优惠券模板管理、用户券发放与核销、复杂结算规则等功能，具备高扩展性和易维护性。

CouponCenter is a distributed coupon center system based on microservices architecture, designed for e-commerce and other platforms requiring flexible coupon management and settlement. It supports coupon template management, user coupon issuance and redemption, complex settlement rules, and is highly extensible and maintainable.

---

## 技术栈 | Tech Stack

- Java 17
- Spring Boot, Spring Cloud Alibaba (Nacos)
- Maven
- MySQL 8
- Docker & Docker Compose
- Lombok, JSR 380 (Bean Validation)
- Swagger (API 文档)

---

## 服务架构 | Service Architecture

本项目采用多微服务架构，主要服务如下：

| 服务名称 | 端口 | 主要职责 | 主要接口前缀 |
|----------|------|----------|--------------|
| coupon-template-service | 20000 | 优惠券模板的创建、克隆、查询、搜索、失效 | `/template` |
| coupon-customer-service | 20001 | 用户券的领取、删除、结算、查询 | `/coupon-customer` |
| coupon-calculation-service | 20002 | 优惠券结算与模拟结算 | `/calculator` |
| Nacos | 8848 | 注册中心与配置中心 | - |
| MySQL | 3306 | 数据存储 | - |

---

## 主要功能 | Main Features

- 优惠券模板的创建、克隆、批量查询、搜索与失效
- 用户优惠券的发放、删除、查询
- 订单结算与多券模拟结算
- 分布式服务注册与配置（Nacos）
- 一键 Docker Compose 启动，自动初始化数据库

---

## 快速开始 | Quick Start

### 1. 克隆项目 Clone the repo

```bash
git clone https://github.com/your-org/CouponCenter.git
cd CouponCenter
```

### 2. 一键启动所有服务（推荐） Start all services (recommended)

确保已安装 Docker 和 Docker Compose。

```bash
docker-compose up -d
```

- 启动后，MySQL 会自动执行 `sql/NacosConfig.sql` 和 `sql/CouponCenterDB.sql` 初始化脚本。
- Nacos、MySQL 及所有微服务会自动注册并启动。

### 3. 本地开发（可选） Local development (optional)

如需本地调试：

```bash
mvn clean install
# 进入各服务模块目录
mvn spring-boot:run
```

### 4. 访问接口文档 API Docs

各服务启动后可通过 `http://localhost:{端口}/swagger-ui.html` 访问接口文档。

---

## 各服务接口说明 | Service API Overview

### coupon-template-service (`/template`)
- `POST /addTemplate`：创建优惠券模板
- `POST /cloneTemplate`：克隆模板
- `GET /getTemplate`：查询模板详情
- `GET /getBatch`：批量查询模板
- `POST /search`：分页搜索模板
- `DELETE /deleteTemplate`：模板失效

### coupon-customer-service (`/coupon-customer`)
- `POST /requestCoupon`：用户领取优惠券
- `DELETE /deleteCoupon`：用户删除优惠券
- `POST /simulateOrder`：模拟订单结算
- `POST /placeOrder`：下单结算
- `POST /findCoupon`：查询用户已领取的优惠券

### coupon-calculation-service (`/calculator`)
- `POST /checkout`：结算购物车
- `POST /simulate`：模拟多券结算

---

## 数据库初始化 | Database Initialization

- 所有初始化 SQL 脚本位于 `sql/` 目录，docker-compose 启动时自动导入。
- 包含 Nacos 配置库和业务库表结构及部分测试数据。

---

## 贡献指南 | Contribution

欢迎提交 issue 和 PR！如有建议或问题请在 issue 区留言。  
We welcome issues and PRs! For suggestions or questions, please open an issue.

---

## License

MIT License
