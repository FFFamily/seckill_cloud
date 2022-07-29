package com.tutu.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tutu.common.exception.BusinessException;
import com.tutu.entity.SeCommodity;
import com.tutu.mapper.SeCommodityMapper;
import com.tutu.vo.SeCommodityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class CommodityService {

    @Resource
    private SeCommodityMapper seCommodityMapper;

    /**
     * 根据编号查询商品
     * @param comId
     * @return
     */
    public SeCommodity findComById(String comId) {
        SeCommodity seCommodity = seCommodityMapper.selectOne(new LambdaQueryWrapper<SeCommodity>().eq(SeCommodity::getId, comId));
        if(Objects.isNull(seCommodity)){
            throw new BusinessException("不存在对应的商品");
        }
        return seCommodity;
    }

    /**
     * 添加商品
     * @param vo
     */
    public void save(SeCommodityVo vo) {
        SeCommodity commodity = new SeCommodity();
        BeanUtils.copyProperties(vo,commodity);
        seCommodityMapper.insert(commodity);
    }
}
