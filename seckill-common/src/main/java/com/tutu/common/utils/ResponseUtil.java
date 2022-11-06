package com.tutu.common.utils;

import com.tutu.common.constants.Constants;
import com.tutu.common.exception.FeignException;
import com.tutu.common.response.BaseResponse;

/**
 * 处理 远程调用 Response
 */
public class ResponseUtil {
    /**
     * 获取实体数据
     */
    public static <T> T getData(BaseResponse<T> response){
        check(response);
        return response.getData();
    }

    /**
     * 检查 Response
     */
    public static void check(BaseResponse response){
        if (response == null){
            throw new FeignException("远程调用异常");
        }
        if (!response.getCode().equals(Constants.SUCCESS)){
            throw new FeignException("远程调用失败,异常原因： "+response.getMessage());
        }
    }
}
