package com.alchemy.usercenter.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alchemy.usercenter.mapper.UserMapper;
import com.alchemy.usercenter.model.User;
import com.alchemy.usercenter.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author KunCheng He
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-08-18 15:24:00
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 盐值
     */
    private final static String SALT = "usercenter";

    @Override
    public Long userRegister(String telPhone, String userPassword, String checkPassword) {
        // 校验用户输入
        if (StrUtil.hasBlank(telPhone, userPassword, checkPassword))
            // 判字段 空
            return (long) -1;
        else if (!Validator.isMobile(telPhone))
            // 校验手机号
            return (long) -2;
        else if (userPassword.length() <8 || userPassword.length() > 16)
            // 校验密码长度
            return (long) -3;
        else if (!userPassword.equals(checkPassword))
            // 校验密码是否一致
            return (long) -4;
        else if (!userPassword.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$"))
            // 校验密码格式
            return (long) -5;
        else {
            // 提前查询用户是否已经注册
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tel_phone", telPhone);
            if (this.count(queryWrapper) > 0)
                // 用户已注册
                return (long) -6;
        }

        // 普通校验通过，对密码进行 MD5 摘要加密（不可解密）
        String password = DigestUtil.md5Hex(SALT + userPassword);

        // 注册用户(注册失败返回 -6，可能是因为该用户以注册)
        User user = new User();
        user.setTelPhone(telPhone);
        user.setPassword(password);
        return this.save(user) ? user.getId() : -7;
    }

    @Override
    public User userLogin(String telPhone, String userPassword, HttpServletRequest request) {
        // 基本校验
        if (StrUtil.hasBlank(telPhone, userPassword))
            return null;
        else if (!Validator.isMobile(telPhone))
            return null;
        else if (!userPassword.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$"))
            return null;

        //  查询用户
        String password = DigestUtil.md5Hex(SALT + userPassword);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tel_phone", telPhone);
        queryWrapper.eq("password", password);
        User user = this.getOne(queryWrapper);

        // 判断用户是否存在
        if (user == null) {
            log.info("用户不存在");
            return null;
        }

        // 脱敏
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setName(user.getName());
        safeUser.setTelPhone(user.getTelPhone());
        safeUser.setEmail(user.getEmail());
        safeUser.setAvatar(user.getAvatar());
        safeUser.setGender(user.getGender());
        safeUser.setStatus(user.getStatus());
        safeUser.setCreateTime(user.getCreateTime());

        // 记录用户登录态
        request.getSession().setAttribute("user", safeUser);
        return safeUser;
    }
}
