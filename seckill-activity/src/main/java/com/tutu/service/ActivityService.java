package com.tutu.service;


import cn.hutool.core.lang.Assert;
import com.tutu.common.entity.PtUser;
import com.tutu.common.halder.PtUserContextHolder;
import com.tutu.common.utils.IdWorkerUtil;
import com.tutu.entity.SeActivity;
import com.tutu.mapper.ActivityMapper;
import com.tutu.mapper.SeActMoneyMapper;
import com.tutu.vo.ActivityVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 涂涂
 * @DateTime: 2022/1/5 20:55
 */
@Slf4j
@Service
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;


//    @Resource
//    private RedisUtil redisUtil;

    @Resource
    private SeActMoneyMapper seActMoneyMapper;

    /**
     * 创建秒杀活动
     *
     * @param vo
     * @return
     */
    @Transactional
    public String save(ActivityVo vo) {
        log.info("创建存入秒杀活动--开始，传入的参数为：{}", vo);
        SeActivity activity = new SeActivity();
        BeanUtils.copyProperties(vo, activity);
        // 拿到当前用户
        PtUser ptUser = PtUserContextHolder.get();
        // 校验用户
        Assert.isTrue(!Objects.isNull(ptUser), "当前用户不存在或未登录");
        // TODO  添加 活动条件
        // 活动条件 ID
        String conId = IdWorkerUtil.nextId();

        // TODO 查询商品表，查看库存是否足够,在正式秒杀前，也得对数量进行判断，否则秒杀不开启
//        SeCommodity seCommodity = commodityMapper.selectOne(new LambdaQueryWrapper<SeCommodity>()
//                .eq(SeCommodity::getId, vo.getComId())
//        );
//        if(Objects.isNull(seCommodity)){
//            throw new BusinessException("不存在对应的商品");
//        }
//        if(seCommodity.getComStock() < vo.getActNum()){
//            throw new BusinessException("商品库存不足，不足以支持对应数目的秒杀");
//        }
        // 赋值
        String id = IdWorkerUtil.nextId();
        activity.setId(id);
        activity.setCreateBy(ptUser.getId());
        activity.setCreateByName(ptUser.getUserName());
        activity.setState(1);
        // TODO 设置创建时间
        activityMapper.insert(activity);
//        getUrl(id);
        // TODO MQ 发送延迟消息，或者使用spring的定时任务
        log.info("创建存入秒杀活动--结束");
        return activity.getId();
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
