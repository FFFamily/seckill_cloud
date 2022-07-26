package com.tutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutu.entity.SeActivity;
import com.tutu.entity.SeUserCommodity;
import com.tutu.response.UserJoinActListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 涂涂
 */
@Mapper
public interface ActivityMapper extends BaseMapper<SeActivity> {
    /**
     * 分页查询活动列表
     *
     * @param page
     * @param id
     * @return
     */
    IPage<UserJoinActListResponse> selectListByUserId(@Param("page") Page<SeUserCommodity> page, @Param("userId") String id);
}
