# CouponCenter - 分布式优惠券中心系统

## 📖 项目简介

CouponCenter 是一个基于微服务架构的分布式优惠券中心系统，专为电商平台设计。系统支持多种优惠券类型、复杂的结算规则、金丝雀发布等企业级特性，具备高扩展性和易维护性。

### 🎯 核心特性
- **多类型优惠券支持**: 满减券、折扣券、随机立减券、午夜双倍券、反PUA券（混入了什么奇怪的东西）
- **复杂结算引擎**: 支持多券叠加、门槛计算、时间规则等
- **金丝雀发布**: 基于流量标记的灰度发布能力
- **微服务架构**: 服务解耦、独立部署、弹性扩展
- **容器化部署**: 一键启动、自动初始化、健康检查

---

## 🏗️ 技术架构

### 技术栈
- **Java**: 17 (主项目) / 8 (部分模块)
- **Spring Boot**: 2.4.2
- **Spring Cloud**: 2020.0.1
- **Spring Data JPA**: 数据访问层
- **Spring Cloud LoadBalancer**: 负载均衡
- **Nacos**: 2.2.3 (服务注册与配置中心)
- **MySQL**: 8.0 (数据存储)
- **Docker & Docker Compose**: 容器化部署
- **Maven**: 项目管理

### 服务架构

| 服务名称 | 端口 | 主要职责 | 接口前缀 | 依赖关系 |
|----------|------|----------|----------|----------|
| coupon-template-service | 20000 | 优惠券模板管理 | `/template` | 独立服务 |
| coupon-customer-service | 20002 | 用户券管理 | `/coupon-customer` | 依赖模板服务、计算服务 |
| coupon-calculation-service | 20001 | 结算计算引擎 | `/calculator` | 独立服务 |
| Nacos | 8848 | 注册中心与配置中心 | - | 基础设施 |
| MySQL | 3306 | 数据存储 | - | 基础设施 |

### 服务依赖关系
```
coupon-customer-service (20002)
├── 调用 coupon-template-service (获取模板信息)
└── 调用 coupon-calculation-service (订单结算)

coupon-calculation-service (20001)
└── 独立服务 (纯计算逻辑)

coupon-template-service (20000)
└── 独立服务 (模板管理)
```

---

## 🎫 优惠券类型详解

### 1. 满减券 (MONEY_OFF)
- **类型代码**: "1"
- **计算规则**: 满指定金额减指定金额
- **示例**: 满100减10元

### 2. 折扣券 (DISCOUNT)
- **类型代码**: "2"
- **计算规则**: 按百分比折扣
- **示例**: 全场8折 (quota=80)

### 3. 随机立减券 (RANDOM_DISCOUNT)
- **类型代码**: "3"
- **计算规则**: 在指定范围内随机减价
- **示例**: 满50随机减1-5元

### 4. 午夜双倍券 (LONELY_NIGHT_MONEY_OFF)
- **类型代码**: "4"
- **计算规则**: 23:00-02:00期间优惠翻倍
- **示例**: 午夜满100减20元

### 5. 反PUA券 (ANTI_PUA)
- **类型代码**: "5"
- **计算规则**: 订单金额乘以996 (特殊规则)
- **示例**: 996暴击优惠

---

## 🔧 核心功能模块

### 🎫 优惠券模板管理 (coupon-template-service)
- **创建模板**: 支持多种优惠券类型创建
- **克隆模板**: 快速复制现有模板
- **批量查询**: 高效获取多个模板信息
- **分页搜索**: 支持条件筛选和分页
- **模板失效**: 软删除机制

### 👤 用户优惠券管理 (coupon-customer-service)
- **领取优惠券**: 用户主动领取，支持数量限制
- **查询优惠券**: 按用户、状态、店铺等条件查询
- **删除优惠券**: 逻辑删除，状态变更
- **优惠券状态**: 可用(1)、已用(2)、已注销(3)

### 🛒 订单结算系统 (coupon-calculation-service)
- **单券结算**: 单个优惠券的价格计算
- **多券模拟**: 支持多券叠加的模拟计算
- **规则引擎**: 基于模板模式的结算规则
- **门槛计算**: 支持最低消费门槛
- **时间规则**: 支持时间相关的优惠规则

### 🔄 金丝雀发布 (Canary Release)
- **流量标记**: 基于 `traffic-version` 的流量路由
- **负载均衡**: 自定义 `CanaryRule` 负载均衡策略
- **灰度测试**: 支持新版本灰度验证
- **服务隔离**: 金丝雀实例与正式实例隔离

---

## 🚀 快速开始

### 环境要求
- Docker & Docker Compose
- 8GB+ 内存
- 10GB+ 磁盘空间

### 一键启动
```bash
# 克隆项目
git clone https://github.com/your-org/CouponCenter.git
cd CouponCenter

# 使用启动脚本 (推荐)
chmod +x start.sh
./start.sh

# 或直接使用 docker-compose
docker-compose up -d
```

### 启动流程
1. **MySQL数据库** (端口: 3306)
   - 自动创建 `nacos_config` 和 `couponcenter_coupon_db` 数据库
   - 执行初始化脚本

2. **Nacos注册中心** (端口: 8848)
   - 用户名/密码: `nacos/nacos`
   - 命名空间: `309879a3-df38-4674-bf53-b59bf125ea97`
   - 分组: `myGroup`

3. **微服务启动**
   - coupon-template-service (20000)
   - coupon-customer-service (20002)
   - coupon-calculation-service (20001)

### 访问地址
| 服务 | 地址 | 说明 |
|------|------|------|
| Nacos控制台 | http://localhost:8848/nacos | 服务注册与配置管理 |
| 模板服务 | http://localhost:20000 | 优惠券模板管理 |
| 客户服务 | http://localhost:20002 | 用户优惠券管理 |
| 计算服务 | http://localhost:20001 | 订单结算计算 |

---

## 📡 API 接口文档

### 优惠券模板服务 (`/template`)
```http
POST /template/addTemplate     # 创建优惠券模板
POST /template/cloneTemplate   # 克隆模板
GET  /template/getTemplate     # 查询模板详情
GET  /template/getBatch        # 批量查询模板
POST /template/search          # 分页搜索模板
DELETE /template/deleteTemplate # 模板失效
```

### 用户优惠券服务 (`/coupon-customer`)
```http
POST /coupon-customer/requestCoupon  # 用户领取优惠券
DELETE /coupon-customer/deleteCoupon # 用户删除优惠券
POST /coupon-customer/simulateOrder  # 模拟订单结算
POST /coupon-customer/placeOrder     # 下单结算
POST /coupon-customer/findCoupon     # 查询用户优惠券
```

### 结算计算服务 (`/calculator`)
```http
POST /calculator/checkout      # 结算购物车
POST /calculator/simulate      # 模拟多券结算
```

### API 测试
项目包含完整的 Postman 测试集合 (`postman_collection.json`)，包含所有接口的测试用例。

---

## 🗄️ 数据库设计

### 数据库结构
- **Nacos配置库**: `nacos_config` (Nacos元数据)
- **业务数据库**: `couponcenter_coupon_db` (业务数据)

---

## 🔧 配置说明

### 环境变量
```bash
SPRING_PROFILES_ACTIVE=docker          # 环境配置
NACOS_SERVER_ADDR=nacos:8848          # Nacos地址
MYSQL_HOST=mysql                       # MySQL主机
MYSQL_PORT=3306                        # MySQL端口
MYSQL_DATABASE=couponcenter_coupon_db  # 数据库名
MYSQL_USERNAME=root                    # 用户名
MYSQL_PASSWORD=root123                 # 密码
```

### Nacos配置
- **命名空间**: `309879a3-df38-4674-bf53-b59bf125ea97`
- **分组**: `myGroup`
- **集群**: `Cluster-A`
- **认证**: 启用 (用户名/密码: nacos/nacos)

### 金丝雀发布配置
- **流量标记**: `traffic-version`
- **负载均衡**: 自定义 `CanaryRule`
- **服务发现**: 基于 Nacos 的服务注册

---

## 🐳 容器化部署

### Docker 镜像构建
```bash
# 构建所有服务镜像
docker-compose build

# 构建特定服务
docker-compose build coupon-template-service
```

### 容器编排
```yaml
# 服务依赖关系
mysql -> nacos -> 微服务

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3
```

### 部署命令
```bash
# 清理数据卷
docker-compose down -v

# 启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看服务日志
docker-compose logs -f [服务名]

# 停止服务
docker-compose down
```

---

## 🔍 监控与日志

### 健康检查
```bash
# 查看服务状态
docker-compose ps

# 查看服务日志
docker-compose logs -f [服务名]

# 查看特定服务日志
docker-compose logs -f coupon-customer-service
```

### 日志级别
- **开发环境**: `DEBUG` (显示SQL、详细调试信息)
- **生产环境**: `INFO` (关键业务日志)

### 监控端点
- **健康检查**: `/actuator/health`
- **应用信息**: `/actuator/info`
- **指标监控**: `/actuator/metrics`

---

## 🧪 测试与验证

### 功能测试
1. **创建优惠券模板**
2. **用户领取优惠券**
3. **模拟订单结算**
4. **实际下单结算**
5. **查询用户优惠券**

### 金丝雀发布测试
1. 启动金丝雀实例 (设置 `traffic-version` 元数据)
2. 发送带 `traffic-version` 标记的请求
3. 验证请求是否路由到金丝雀实例
4. 监控金丝雀实例的日志和性能

### 性能测试
- **并发测试**: 多用户同时领券、下单
- **压力测试**: 高并发场景下的系统表现
- **稳定性测试**: 长时间运行的系统稳定性

---

## 🛠️ 开发指南

### 项目结构
```
CouponCenter/
├── coupon-center/                           # 主项目目录
│   ├── coupon-template-service/             # 模板服务
│   │   ├── coupon-template-service-api/     # API定义
│   │   ├── coupon-template-service-dao/     # 数据访问层
│   │   └── coupon-template-service-impl/    # 业务实现
│   ├── coupon-customer-service/             # 客户服务
│   │   ├── coupon-customer-service-api/     # API定义
│   │   ├── coupon-customer-service-dao/     # 数据访问层
│   │   └── coupon-customer-service-impl/    # 业务实现
│   ├── coupon-calculation-service/          # 计算服务
│   │   ├── coupon-calculation-service-api/  # API定义
│   │   └── coupon-calculation-service-impl/ # 业务实现
│   └── Dockerfile                          # 容器构建文件
├── sql/                                    # 数据库脚本
│   ├── nacos/                             # Nacos配置库脚本
│   └── business/                          # 业务数据库脚本
├── docker-compose.yml                     # 容器编排
├── start.sh                               # 启动脚本
├── postman_collection.json               # API测试集合
└── README.md                             # 项目文档
```

### 本地开发
```bash
# 启动依赖服务
docker-compose up mysql nacos -d

# 编译项目
mvn clean install

# 启动各服务 (分别在不同终端)
cd coupon-center/coupon-template-service/coupon-template-service-impl
mvn spring-boot:run

cd coupon-center/coupon-customer-service/coupon-customer-service-impl
mvn spring-boot:run

cd coupon-center/coupon-calculation-service/coupon-calculation-service-impl
mvn spring-boot:run
```

---

## 🤝 贡献指南

### 开发流程
1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

### 代码审查
- 确保代码符合项目规范
- 添加必要的测试用例
- 更新相关文档
- 通过 CI/CD 检查

### 问题反馈
- 使用 GitHub Issues 报告问题
- 提供详细的错误信息和复现步骤
- 标注问题类型和优先级

---

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

---

## 📞 联系方式

- 项目主页: https://github.com/your-org/CouponCenter
- 问题反馈: https://github.com/your-org/CouponCenter/issues
- 邮箱: your-email@example.com

---

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者和用户！
