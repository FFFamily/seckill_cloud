package com.tutu.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutu.entity.SeActLog;
import com.tutu.mapper.SeActLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author
 * @Date 2021/12/31 14:09
 */
@Slf4j
@Service
public class SeActLogService {

    @Resource
    private SeActLogMapper seActLogMapper;


    /**
     * 分页条件查询活动记录
     *
     * @param vo
     * @return
     */
    public BasePage selectList(ActLogListParamVo vo, String actId) {
        Page<SeActLog> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        IPage<SeActLog> seActLogIPage = seActLogMapper.selectPage(page,
                new LambdaQueryWrapper<SeActLog>().eq(SeActLog::getActId, actId));
        return BasePage.getPage(seActLogIPage);
    }
}
