#!/bin/bash

echo "🚀 开始启动优惠券中心Docker环境..."

# 检查Docker是否运行
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker未运行，请先启动Docker"
    exit 1
fi

# 检查Docker Compose是否安装
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose未安装，请先安装Docker Compose"
    exit 1
fi

# 停止并删除现有容器
echo "🔄 清理现有容器..."
docker-compose down -v

# 构建并启动服务
echo "🔨 构建并启动服务..."
docker-compose up --build -d

# 等待服务启动
echo "⏳ 等待服务启动..."
sleep 30

# 检查服务状态
echo "📊 检查服务状态..."
docker-compose ps

echo "✅ 服务启动完成！"
echo ""
echo "🌐 访问地址："
echo "  - Nacos控制台: http://localhost:8848/nacos (用户名/密码: nacos/nacos)"
echo "  - 模板服务: http://localhost:20000"
echo "  - 客户服务: http://localhost:20001"
echo "  - 计算服务: http://localhost:20002"
echo ""
echo "📝 查看日志："
echo "  - 查看所有服务日志: docker-compose logs -f"
echo "  - 查看特定服务日志: docker-compose logs -f [服务名]"
echo ""
echo "🛑 停止服务："
echo "  - docker-compose down" 