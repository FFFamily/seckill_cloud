package com.tutu.service;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.tutu.common.response.BaseResponse;
import com.tutu.domain.SeUserDto;
import com.tutu.domain.SecurityUser;
import com.tutu.domain.UserDTO;
import com.tutu.feign.UserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户信息加载
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserFeign userFeign;


    // TODO 这里出现异常不会有提示

    /**
     * 我这里 用户名 就是 手机号
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询数据库
        BaseResponse res = userFeign.getUserByUserPhone(username);
        String jsonStr = JSONUtil.toJsonStr(res.getData());
        SeUserDto data = JSON.parseObject(jsonStr, SeUserDto.class);
        UserDTO userDTO = new UserDTO();
        // TODO 这里需要将 userDTO 移除
        BeanUtils.copyProperties(data,userDTO);
        SecurityUser securityUser = new SecurityUser(userDTO);
//        securityUser.setEnabled(true);
//        if (!securityUser.isEnabled()) {
//            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
//        } else if (!securityUser.isAccountNonLocked()) {
//            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
//        } else if (!securityUser.isAccountNonExpired()) {
//            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
//        } else if (!securityUser.isCredentialsNonExpired()) {
//            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
//        }
        log.info("Oauth模块 查询用户: {}，其信息为，{}",username,securityUser);
        return securityUser;
    }

}

