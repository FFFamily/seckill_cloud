//package com.tutu.service;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.example.miaosha.common.constants.Constants;
//import com.example.miaosha.common.exception.BusinessException;
//import com.example.miaosha.common.token.PtUserContextHolder;
//import com.example.miaosha.common.utils.DateUtil;
//import com.example.miaosha.common.utils.IdWorkerUtil;
//import com.example.miaosha.entity.SeActLog;
//import com.example.miaosha.entity.SeActivity;
//import com.example.miaosha.entity.SeUser;
//import com.example.miaosha.entity.SeUserCommodity;
//import com.example.miaosha.entity.user.PtUser;
//import com.example.miaosha.mapper.ActivityMapper;
//import com.example.miaosha.mapper.SeActLogMapper;
//import com.example.miaosha.mapper.SeUserCommodityMapper;
//import com.example.miaosha.mapper.SeUserMapper;
//import com.example.miaosha.response.UserJoinActListResponse;
//import com.example.miaosha.response.base.BasePage;
//import com.example.miaosha.vo.ActPassVo;
//import com.example.miaosha.vo.ActivitylistParamVo;
//import com.example.miaosha.vo.JoinActivityVo;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Objects;
//import java.util.TimeZone;
//
///**
// * @Author zhangBoKai
// * @Date 2022/1/14 14:15
// */
//@Service
//public class UserCommodityService {
//
//    @Resource
//    SeUserMapper seUserMapper;
//    @Resource
//    private SeUserCommodityMapper seUserCommodityMapper;
//    @Resource
//    private ActivityMapper activityMapper;
//    @Resource
//    private SeActLogMapper seActLogMapper;
//    @Resource
//    private SeLoanRecordService seLoanRecordService;
//
//    /**
//     * 参加活动
//     *
//     * @param vo
//     */
//    public void save(JoinActivityVo vo) {
//        // 拿到当前用户
//        PtUser ptUser = PtUserContextHolder.get();
//        if (Objects.isNull(ptUser)) {
//            throw new BusinessException("用户未找到");
//        }
//
//        // 条件 筛选
//        Boolean flag = seLoanRecordService.screenRecord(ptUser.getId());
//
//        // 拿到对应的活动
//        SeActivity seActivity = activityMapper.selectOne(new LambdaQueryWrapper<SeActivity>()
//                .eq(SeActivity::getId, vo.getActivityId())
//                .eq(SeActivity::getIsDeleted, Constants.NO)
//        );
//        if (Objects.isNull(seActivity)) {
//            throw new BusinessException("活动未找到");
//        }
//
//        SeUserCommodity seUserCommodity = seUserCommodityMapper.selectOne(new LambdaQueryWrapper<SeUserCommodity>()
//                .eq(SeUserCommodity::getUserId, ptUser.getId())
//                .eq(SeUserCommodity::getActId, vo.getActivityId())
//                .eq(SeUserCommodity::getIsDeleted, Constants.NO)
//        );
//        if (!Objects.isNull(seUserCommodity)) {
//            throw new BusinessException("用户已经参与活动了，不能再次参与");
//        }
//        SeUserCommodity userCommodity = new SeUserCommodity();
//        userCommodity.setId(IdWorkerUtil.nextId());
//        userCommodity.setUserId(ptUser.getId());
//        userCommodity.setActId(seActivity.getId());
//        userCommodity.setIsDeleted(false);
//        seUserCommodityMapper.insert(userCommodity);
//    }
//
//    /**
//     * 放弃参与活动
//     *
//     * @param vo
//     */
//    public void giveUp(JoinActivityVo vo) {
//        SeActivity seActivity = activityMapper.selectOne(new LambdaQueryWrapper<SeActivity>()
//                .eq(SeActivity::getId, vo.getActivityId())
//                .eq(SeActivity::getIsDeleted, Constants.NO)
//        );
//        if (Objects.isNull(seActivity)) {
//            throw new BusinessException("活动未找到");
//        }
//        PtUser ptUser = PtUserContextHolder.get();
//        if (Objects.isNull(ptUser)) {
//            throw new BusinessException("用户未找到");
//        }
//        SeUserCommodity seUserCommodity = seUserCommodityMapper.selectOne(
//                new LambdaQueryWrapper<SeUserCommodity>()
//                        .eq(SeUserCommodity::getActId, vo.getActivityId())
//                        .eq(SeUserCommodity::getUserId, ptUser.getId())
//                        .eq(SeUserCommodity::getIsDeleted, Constants.NO)
//        );
//        if (Objects.isNull(seUserCommodity)) {
//            throw new BusinessException("你没有参与过对应的活动");
//        }
//        seUserCommodity.setIsDeleted(true);
//        seUserCommodityMapper.updateById(seUserCommodity);
//    }
//
//    /**
//     * 拿到用户参与活动列表
//     *
//     * @param vo
//     * @return
//     */
//    public BasePage findActivitys(ActivitylistParamVo vo) {
//        PtUser ptUser = PtUserContextHolder.get();
//        if (Objects.isNull(ptUser)) {
//            throw new BusinessException("用户未找到");
//        }
//        Page<SeUserCommodity> page = new Page<>(vo.getPageNum(), vo.getPageSize());
//        // 查询用户-活动关联表
//        IPage<UserJoinActListResponse> iPage = activityMapper.selectListByUserId(page, ptUser.getId());
//        return new BasePage(iPage);
//    }
//
//    /**
//     * 获取所有的列表
//     *
//     * @param vo
//     * @return
//     */
//    public BasePage findAllActivitys(ActivitylistParamVo vo) {
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        String format = sdf.format(date);
//        System.out.println("------------"+date);
//
//        LambdaQueryWrapper<SeActivity> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SeActivity::getIsDeleted, Constants.NO)
//                .ge(SeActivity::getActEnd, format)
//                .orderByAsc(SeActivity::getActStart);
//
//        Page<SeActivity> page = new Page<SeActivity>(vo.getPageNum(), vo.getPageSize());
//        IPage<SeActivity> iPage = activityMapper.selectPage(page, queryWrapper);
//
//        return BasePage.getPage(iPage);
//    }
//
//    public ActPassVo findActivity(String actId) {
//        PtUser ptUser = PtUserContextHolder.get();
//        if (Objects.isNull(ptUser)) {
//            throw new BusinessException("用户未找到");
//        }
//        SeActLog seActLog = new SeActLog();
//        seActLog.setId(IdWorkerUtil.nextId());
//        seActLog.setActId(actId);
//        seActLog.setUserId(ptUser.getId());
//        seActLog.setWatchTime(DateUtil.getDate());
//        seActLogMapper.insert(seActLog);
//
//        SeActivity seActivity = activityMapper.selectById(actId);
//        ActPassVo actPassVo = new ActPassVo();
//        BeanUtils.copyProperties(seActivity, actPassVo);
//        actPassVo.setPass(getPass(seActivity, ptUser.getId()));
//        return actPassVo;
//    }
//
//    public int getPass(SeActivity seActivity, String userId) {
//        int flag = 0;
//        if (seActivity.getIsFitter() == 1) {
//            SeUser seUser = seUserMapper.selectById(userId);
//            if (seUser.getYear() > seActivity.getLimitYear()) {
//                flag = 1;
//            }
//            if (seActivity.getWorkState() != 4) {
//                if (!seUser.getWorkState().equals(seActivity.getWorkState())) {
//                    flag = 1;
//                }
//            }
//            if (seActivity.getPromiseState() != 3) {
//                if (!seUser.getPromiseState().equals(seActivity.getPromiseState())) {
//                    flag = 1;
//                }
//            }
//            if (seUser.getLoanTime() > seActivity.getLoanTime()) {
//                flag = 1;
//            }
//        }
//        return flag != 0 ? 0 : 1;
//    }
//}
