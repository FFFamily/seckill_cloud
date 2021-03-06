# seckill_cloud

项目流程
- 版本 1.1.1 修复了 模块调用401的异常，原因是模块意外导入了security依赖，但没有配置security
- 携带 token 访问，一直提示Not Authenticated ，原因是 从 WebFilterChain 中 遍历找 Authorization 类 为空
- 版本 1.1.2 修复了携带token 也无法访问的问题 ，添加了AuthorizationManager鉴权管理器
- 版本 1.1.2 遗留 BUG，获取用户信息乱码问题
- 版本 1.1.3 解决用户信息乱码问题，在Gateway拦截解析token重新构建请求头时出现了乱码
- 版本 1.1.4 秒杀活动模块正常运行版本
- 版本 1.1.5 秒杀活动、商品的最基本功能完成
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
- 完成 

## 秒杀活动
1，管理员能够控制秒杀的打开与关闭，进行手动的管理
2，管理员能够可以手动设置一些校验参数，不满足条件的人无法参与
3，用户和管理员能直观的看到秒杀活动的参与人数
4，动态生成秒杀链接分享
## 优惠券派发

## 待解决
- redis 会出现连接太久的情况： redis-cli -h 101.34.91.181 6379 --intrinsic-latency 60
