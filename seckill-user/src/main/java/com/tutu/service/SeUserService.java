package com.tutu.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tutu.common.enums.UserTypeEnum;
import com.tutu.common.exception.BusinessException;
import com.tutu.common.exception.LoginException;
import com.tutu.entity.SeUser;
import com.tutu.mapper.SeUserMapper;
import com.tutu.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public SeUser login(LoginVo vo) {
        log.info("登录--开始，参数为：{}", vo);
        SeUser seUser = seUserMapper.selectOne(
                new LambdaQueryWrapper<SeUser>().eq(SeUser::getPhone, vo.getPhone())
        );
        if (Objects.isNull(seUser)) {
            throw new LoginException("用户未找到");
        }
        if (!vo.getPassWord().equals(seUser.getPassWord())) {
            throw new LoginException("密码或用户名错误");
        }
        seUser.setPassWord(null);
        seUser.setPromiseState(null);
        seUser.setLoanTime(null);
        seUser.setWorkState(null);
        log.info("登录--结束");
        return seUser;
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
}
