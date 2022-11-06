package com.tutu.user.service;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tutu.common.enums.UserTypeEnum;
import com.tutu.common.exception.BusinessException;
import com.tutu.common.exception.LoginException;
import com.tutu.common.response.BaseResponse;
import com.tutu.common.utils.RedisUtil;
import com.tutu.common.utils.ResponseUtil;
import com.tutu.user.dto.TokenDto;
import com.tutu.user.entity.SeUser;
import com.tutu.user.feign.UserFeign;
import com.tutu.user.mapper.SeUserMapper;
import com.tutu.user.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

/**
 * @Author
 * @Date 2021/12/31 14:09
 */
@Slf4j
@Service
public class SeUserService {

    @Resource
    private SeUserMapper seUserMapper;

    @Autowired
    private UserFeign userFeign;

    @Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
    private String basicToken;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 根据手机号查询用户
     *
     * @param phone
     * @return
     */
    public SeUser findUserByPhone(String phone) {
        SeUser seUser = seUserMapper.selectOne(new LambdaQueryWrapper<SeUser>().eq(SeUser::getPhone, phone));
        return seUser;
    }

    /**
     * 登录
     *
     * @param vo
     * @return
     */
    public String login(LoginVo vo) {
        log.info("登录，参数为：{}", vo);
        SeUser seUser = seUserMapper.selectOne(new LambdaQueryWrapper<SeUser>().eq(SeUser::getPhone, vo.getPhone()));
        if (seUser == null){
            throw new LoginException("用户不存在");
        }
        if (redisUtil.hasKey(seUser.getId())) {
            log.error("重复用户的Token为 : {}",redisUtil.get(seUser.getId()));
            redisUtil.remove(seUser.getId());
            throw new LoginException("用户已登录,请不要重复登录");
        }
        BaseResponse<TokenDto> res = userFeign.getToken("password", vo.getPhone(), vo.getPassWord(), "123456", "client-app");
        TokenDto data = ResponseUtil.getData(res);
        if (ObjectUtil.isNull(data)){
            throw new LoginException("用户名或密码错误");
        }
        String token = data.getTokenHead() + data.getToken();
        log.info("登录结束");
        return token;
    }

    /**
     * 注册
     *
     * @param vo
     * @return
     */
    public String register(LoginVo vo) {
        log.info("注册--开始，参数为：{}", vo);
        SeUser seUser = seUserMapper.selectOne(
                new LambdaQueryWrapper<SeUser>().eq(SeUser::getPhone, vo.getPhone())
        );
        if (Objects.nonNull(seUser)) {
            //TODO 需要返回给前端错误信息
            throw new BusinessException("手机号被使用，请重新注册");
        }
        SeUser user = new SeUser();
        BeanUtils.copyProperties(vo, user);
        user.setIsAdmin(UserTypeEnum.USER.getCode());
        user.setYear((int) (15 + Math.random() * (80 - 15) + 1));
        if (user.getYear() < 18) {
            user.setWorkState(1);
        } else {
            user.setWorkState((int) (1 + Math.random() * (3 - 1) + 1));
        }
        int ranLo = new Random().nextInt(100);
        if (user.getYear() > 18) {
            if (ranLo < 10) {
                user.setLoanTime(ranLo % 10);
            } else {
                user.setLoanTime(0);
            }
            if (ranLo < 50) {
                user.setPromiseState(0);
            } else {
                user.setLoanTime(1);
            }
        }
        user.setMoney(new BigDecimal("100"));
        // 加密密码
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        seUserMapper.insert(user);
        return user.getId();
    }


    /**
     * 更新用户IP
     */
    public void updateUser(SeUser seUser) {
        seUserMapper.updateById(seUser);
    }

    /**
     * 通过用户ID查到用户
     *
     * @param id
     * @return
     */
    public SeUser findUserById(String id) {
        return seUserMapper.selectOne(new LambdaQueryWrapper<SeUser>().eq(SeUser::getId, id));
    }

    /**
     * 获取用户通过用户名
     * @param userName
     * @return
     */
    public SeUser findUserByName(String userName) {
        return seUserMapper.selectOne(new LambdaQueryWrapper<SeUser>().eq(SeUser::getUserName, userName));
    }
}
