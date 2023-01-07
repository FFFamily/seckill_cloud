package com.tutu.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tutu.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author
 * @Date 2021/12/31 14:10
 */
@Mapper
public interface SeUserMapper extends BaseMapper<User> {
}
