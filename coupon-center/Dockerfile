# 多阶段构建
FROM maven:3.8.6-openjdk-17 AS builder

# 设置工作目录
WORKDIR /app

# 复制pom文件
COPY pom.xml .
COPY coupon-template-service/pom.xml coupon-template-service/
COPY coupon-customer-service/pom.xml coupon-customer-service/
COPY coupon-calculation-service/pom.xml coupon-calculation-service/

# 下载依赖
RUN mvn dependency:go-offline -B

# 复制源代码
COPY . .

# 构建参数
ARG SERVICE_NAME
ENV SERVICE_NAME=${SERVICE_NAME}

# 构建项目
RUN mvn clean package -DskipTests -pl ${SERVICE_NAME} -am

# 运行阶段
FROM openjdk:17-jre-slim

# 设置工作目录
WORKDIR /app

# 安装必要的工具
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# 创建非root用户
RUN groupadd -r appuser && useradd -r -g appuser appuser

# 从构建阶段复制jar文件
COPY --from=builder /app/${SERVICE_NAME}/${SERVICE_NAME}-impl/target/*.jar app.jar

# 设置权限
RUN chown -R appuser:appuser /app
USER appuser

# 暴露端口
EXPOSE 20000 20001 20002

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:${SERVER_PORT:-20000}/actuator/health || exit 1

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"] 