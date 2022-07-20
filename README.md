# seckill_cloud


集成 GateWay + Oauth2 做权限认证
- gateway 的逻辑是 先判断是否携带 token
- 完成登录注册，密码加密，登录返回token 
- 携带token访问接口，全局识别token，存放用户信息，以供其他模块访问用户信息
- 版本 1.0.1 修复了 模块调用401的异常，原因是模块意外导入了security依赖，但没有配置security


集成 Nocos 做 注册中心 以及 配置中心
- Nacos 服务注册，OpenFeign 实现远程调用
  - 版本问题，高版本的Cloud没有使用 Ribbon 做负载均衡，以及hystrix做熔断
  - 需要改用 Sentinel
- 完成 Nacos 配置中心，将配置文件放入 Nacos 中
- 完成 