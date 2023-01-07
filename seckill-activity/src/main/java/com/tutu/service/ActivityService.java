package com.tutu.service;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tutu.common.constants.Constants;
import com.tutu.common.exception.BusinessException;
import com.tutu.common.halder.UserInfoHandler;
import com.tutu.common.utils.CopyUtil;
import com.tutu.entity.Activity;
import com.tutu.entity.Commodity;
import com.tutu.mapper.ActivityMapper;
import com.tutu.vo.ActivityListParamVo;
import com.tutu.vo.ActivityVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 涂涂
 * @DateTime: 2022/1/5 20:55
 */
@Slf4j
@Service
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Resource
    private CommodityService commodityService;

    /**
     * 创建秒杀活动
     *
     * @param vo
     * @return
     */
    @Transactional
    public void save(ActivityVo vo) {
        log.info("创建存入秒杀活动--开始，传入的参数为：{}", vo);
        Activity activity = new Activity();
        CopyUtil.Copy(vo, activity);
        // 拿到当前用户
        String userId = UserInfoHandler.getUserId();
        // 查询到商品信息
        Commodity commodity = commodityService.findComById(vo.getComId());
        // TODO 查询所有 查询所有包含该商品的活动所销售的商品数量
        if (commodity.getComStock() < vo.getActNum()) {
            throw new BusinessException("商品库存不足，不足以支持对应数目的秒杀");
        }
        activity.setState(Integer.valueOf(Constants.NO));
        activityMapper.insert(activity);
        // TODO MQ 发送延迟消息，或者使用spring的定时任务
        log.info("创建存入秒杀活动--结束");
    }

    /**
     * 根据活动编号查询活动信息
     *
     * @param actId
     * @return
     */
    public Activity findActivityById(String actId) {
        Activity activity = activityMapper.selectOne(new LambdaQueryWrapper<Activity>().eq(Activity::getId, actId));
        if (activity == null) {
            // todo 这里活动超时的查询也要添加进去
            throw new RuntimeException("活动不存在或者已经结束");
        }
        return activity;
    }

    /**
     * 根据活动编号删除活动
     *
     * @param id
     * @return
     */
    public Activity deleteById(String id) {
        Activity activity = activityMapper.selectOne(new LambdaQueryWrapper<Activity>().eq(Activity::getId, id));
        if (ObjectUtil.isNull(activity)) {
            throw new BusinessException("对应活动不存在");
        }
        activityMapper.deleteById(id);
        return activity;
    }

    /**
     * 条件查询活动列表
     *
     * @param vo
     * @return
     */
    public IPage<Activity> findAllActivities(ActivityListParamVo vo) {
        Page page = new Page(vo.getPageNum(), vo.getPageSize());
        IPage<Activity> seActivityIPage = activityMapper.selectPage(page,
                new LambdaQueryWrapper<Activity>()
                        .eq(StrUtil.isNotBlank(vo.getComId()), Activity::getComId, vo.getComId())
                        .eq(StrUtil.isNotBlank(vo.getActTitle()), Activity::getActTitle, vo.getActTitle()));
        return seActivityIPage;
    }

//    public String getUrl(String id) {
//        String[] res = ShortUrl.shortUrl("/seckill/dynamicBuy/"+id);
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("/seckill/dynamicBuy/");
//        for (String str : res) {
//            stringBuffer.append(str);
//        }
//        get(stringBuffer.toString(),id);
//        return stringBuffer.toString();
//    }
//
//    @Resource
//    private SeUrlMapper seUrlMapper;
//    private static final String url = "/seckill/buy";
//    private static final String decUrl = "/seckill/dynamicBuy/";
//
//    public void get(String oldUrl,String actId) {
//        SeUrl seUrl = new SeUrl();
//        seUrl.setId(IdWorkerUtil.nextId());
//        seUrl.setOldUrl(oldUrl);
//        seUrl.setNewUrl(url);
//        seUrl.setActId(actId);
//        seUrl.setState(Integer.parseInt(Constants.YES));
//        SeUrl old = seUrlMapper.selectOne(new LambdaQueryWrapper<SeUrl>().eq(SeUrl::getActId, actId));
//        if (Objects.nonNull(old)) {
//            throw new BusinessException("请不要重复给相同的活动添加短链接");
//        }
//        seUrlMapper.insert(seUrl);
//    }
//
//    /**
//     * 通过活动编号查询活动
//     *
//     * @param actId
//     * @return
//     */
//    public SeActivity getOne(String actId) {
//        return activityMapper.selectOne(new LambdaQueryWrapper<SeActivity>().eq(SeActivity::getId, actId));
//    }
//
//    /**
//     * 预热数据到Redis中
//     *
//     * @param vo
//     * @return
//     */
//    public SeckillActivityVo putData(ActivityPutVo vo) {
//        SeckillActivityVo seckillActivityVo = new SeckillActivityVo();
//        SeActivity seActivity = activityMapper.selectOne(new LambdaQueryWrapper<SeActivity>().eq(SeActivity::getId, vo.getActId()));
//        if (Objects.isNull(seActivity)) {
//            throw new BusinessException("没有找到对应的秒杀活动信息");
//        }
//        seckillActivityVo.setSeActStock(seActivity.getActNum());
//        redisUtil.set(RedisEnums.SECKILL_ACTIVITY + vo.getActId(), JSONObject.toJSON(seckillActivityVo));
//        redisUtil.set(RedisEnums.DECKILL_ACT_NUM + vo.getActId(), seActivity.getActNum());
//        return seckillActivityVo;
//    }
//
//    public BaseResponse delete(String actId) {
//        if (activityMapper.deleteById(actId) > 0) {
//            return BaseResponse.success("成功删除", null);
//        }
//        return BaseResponse.success("请勿重复删除", null);
//    }
//
//    public SeActivity expose(ActivityPutVo vo) {
//        SeActivity seActivity = activityMapper.selectById(vo.getActId());
//        int open = seActivity.getIsOpen();
//        if(open==1){
//            seActivity.setIsOpen(0);
//        }else{
//            seActivity.setIsOpen(1);
//        }
//        activityMapper.updateById(seActivity);
//        return seActivity;
//    }

    /**
     * 获取Url
     * @param id
     * @return
     */
//    private String getUrl(String id) {
//        String[] res = ShortUrl.shortUrl("/seckill/dynamicBuy/"+id);
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("/seckill/dynamicBuy/");
//        for (String str : res) {
//            stringBuffer.append(str);
//        }
//        get(stringBuffer.toString(),id);
//        return stringBuffer.toString();
//    }
}
