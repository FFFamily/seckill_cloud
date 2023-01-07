package com.tutu.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tutu.common.exception.BusinessException;
import com.tutu.common.halder.UserInfoHandler;
import com.tutu.entity.Commodity;
import com.tutu.mapper.SeCommodityMapper;
import com.tutu.vo.CommodityListVo;
import com.tutu.vo.SeCommodityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class CommodityService {

    @Resource
    private SeCommodityMapper seCommodityMapper;

    /**
     * 根据编号查询商品
     *
     * @param comId
     * @return
     */
    public Commodity findComById(String comId) {
        Commodity commodity = seCommodityMapper.selectOne(new LambdaQueryWrapper<Commodity>().eq(Commodity::getId, comId));
        if (Objects.isNull(commodity)) {
            throw new BusinessException("不存在对应的商品");
        }
        return commodity;
    }

    /**
     * 添加商品
     *
     * @param vo
     */
    public void save(SeCommodityVo vo) {
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(vo, commodity);
        commodity.setCreateBy(UserInfoHandler.getUserId());
        seCommodityMapper.insert(commodity);
    }

    /**
     * 查询商品集合（分页）
     *
     * @param vo
     * @return
     */
    public List<Commodity> findComList(CommodityListVo vo) {
        String userId = UserInfoHandler.getUserId();
        return seCommodityMapper.selectList(new LambdaQueryWrapper<Commodity>().eq(Commodity::getCreateBy, userId));
    }
}
