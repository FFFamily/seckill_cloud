package com.tutu.feign;

import com.tutu.common.response.BaseResponse;
import com.tutu.dto.SeActivityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "activity-server")
public interface ActivityFeign {

    /**
     * 获取活动
     * @param id
     * @return
     */
    @GetMapping("/activity/getAct/{id}")
    BaseResponse<SeActivityDto> getActivityInfo(@PathVariable(value = "id") String id);
}
