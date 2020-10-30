# 层次如下
* aidoudong-spring-cloud
* -- aidoudong-manager ：后台管理，不需要通过认证服务器，通过springsecurity实现的
* -- aidoudong-common ：公共模块
* -- aidoudong-business ：业务模块
* -- -- aidoudong-client ：资源消费者，需要通过认证服务器才能调用资源生产者
* -- -- aidoudong-api ：资源生产者，需要配置资源信息
* -- -- aidoudong-oauth2-server ：认证服务器
* -- -- aidoudong-discovery-eureka ：服务器发现组件
* -- -- aidoudong-discovery-zuul ：服务路由
* -- -- aidoudong-discovery-admin ：未完成
# 运行环境
* jdk8,maven
# 更新日志
* -- 20201029
* -- -- 抽离公共包（aidoudong-common）
* -- 20201030
* -- -- 调整层级结构
