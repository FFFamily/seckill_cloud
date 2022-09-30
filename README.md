# seckill_cloud
本项目是自己之前的秒杀项目的分布式版本
自己分布式经验欠缺，那么本项目便是作为自己分布式学习中的一个实践



项目流程
- 版本 1.1.1 修复了 模块调用401的异常，原因是模块意外导入了security依赖，但没有配置security
- 携带 token 访问，一直提示Not Authenticated ，原因是 从 WebFilterChain 中 遍历找 Authorization 类 为空
- 版本 1.1.2 修复了携带token 也无法访问的问题 ，添加了AuthorizationManager鉴权管理器
- 版本 1.1.2 遗留 BUG，获取用户信息乱码问题
- 版本 1.1.3 解决用户信息乱码问题，在Gateway拦截解析token重新构建请求头时出现了乱码
- 版本 1.1.4 秒杀活动模块正常运行版本
- 版本 1.1.5 秒杀活动、商品的最基本功能完成
- 版本 1.1.6 集成Rabbitmq，实现异步发送消息创建订单
- 版本 1.1.7 优化用户认证流程: 重复登录提示、登录过期提示、ThreadLocal存放用户信息逻辑

## 集成 GateWay + Oauth2 做权限认证
- gateway 的逻辑是 先判断是否携带 token
- 完成登录注册，密码加密，登录返回token 
- 携带token访问接口，全局识别token，存放用户信息，以供其他模块访问用户信息
### 遗留的问题
- 获取用户的方式不对，现在是走的请求头拿信息，没有过缓存，要优化

## 集成 Nocos 做 注册中心 以及 配置中心
- Nacos 服务注册，OpenFeign 实现远程调用
  - 版本问题，高版本的Cloud没有使用 Ribbon 做负载均衡，以及hystrix做熔断
  - 需要改用 Sentinel
- 完成 Nacos 配置中心，将配置文件放入 Nacos 中

## 相关的问题整理
### 秒杀活动
- 管理员能够控制秒杀的打开与关闭，进行手动的管理
- 管理员能够可以手动设置一些校验参数，不满足条件的人无法参与
- 用户和管理员能直观的看到秒杀活动的参与人数
- 动态生成秒杀链接分享

### 秒杀

### 优惠券派发

### 站内信

### 配置化
- 秒杀的方式有很多，本项目中将这些项目理解为"策略"，可以选择不同的策略来实现秒杀的功能，如何动态的切换这些配置，并减少代码的冗余

### 链路追踪/日志
- 想新起一个微服务，专门做秒杀的一个数据监听，统计一些秒杀数据：秒杀通过率、失败率、总数...
那么就需要对某些接口监听，生成日志

### 待解决
- redis 会出现连接太久的情况： redis-cli -h 101.34.91.181 6379 --intrinsic-latency 60
- 全局异常没有处理到位
- 生成订单为什么不异步而是使用mq发消息呢，消息中间件起到什么作用呢?
- 消费消息出错会导致消息丢失
- 消费消息一直显示 序列化 的错误，将其改为传递字符串，那么两者有效率相关的问题吗？一个序列化后的二进制？一个字符串（字符串是否也会被序列化）
- 分布式事务问题（MQ，人工兜底）
- 引入动态化配置
- 美团动态线程池
- 需要对远程调用做处理，远程调用的接口直接返回的response，接收需要使用json接收，所以可以考虑用aop做一层切面