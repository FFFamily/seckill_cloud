package com.tutu.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tutu.common.response.BaseResponse;
import com.tutu.constant.MessageConstant;
import com.tutu.domain.SeUserDto;
import com.tutu.domain.SecurityUser;
import com.tutu.domain.UserDTO;
import com.tutu.feign.UserFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息加载
 */
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
        return securityUser;
    }

}

