package com.tutu.feign;

import com.tutu.handler.VodClientDefeat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 登录方法
 */
//调用的服务的名称,以及熔断后执行方法的类
@FeignClient(name = "user-server", fallback = VodClientDefeat.class)
@Component
public interface userFeign {

}
