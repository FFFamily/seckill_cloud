package com.tutu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tutu.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeCommodityMapper extends BaseMapper<Commodity> {
}
