package com.tutu.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.miaosha.entity.SeActMoney;
import com.example.miaosha.mapper.SeActMoneyMapper;
import com.example.miaosha.response.base.BasePage;
import com.example.miaosha.vo.ActMoneyListParamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author
 * @Date 2021/12/31 14:09
 */
@Slf4j
@Service
public class SeActMoneyService {

    @Resource
    private SeActMoneyMapper seActMoneyMapper;


    /**
     * @param vo
     * @return
     */
    public BasePage selectList(ActMoneyListParamVo vo) {
        Page<SeActMoney> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        IPage<SeActMoney> seActMoneyIPage = seActMoneyMapper.selectPage(page,
                new LambdaQueryWrapper<SeActMoney>());
        return BasePage.getPage(seActMoneyIPage);
    }
}
