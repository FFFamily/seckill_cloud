package com.tutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tutu.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 涂涂
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
    /**
     * 分页查询活动列表
     *
     * @param page
     * @param id
     * @return
     */
//    IPage<UserJoinActListResponse> selectListByUserId(@Param("page") Page<SeUserCommodity> page, @Param("userId") String id);
}
