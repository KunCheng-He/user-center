package com.alchemy.usercenter.service;

import com.alchemy.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author KunCheng He
* @description 针对表【user】的数据库操作Service
* @createDate 2024-08-18 15:24:00
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param telPhone 电话号码
     * @param userPassword 用户密码
     * @param checkPassword 用户确认密码
     * @return 用户的 ID
     */
    Long userRegister(String telPhone, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param telPhone 电话号码
     * @param userPassword 用户密码
     * @param request 请求
     * @return 用户信息
     */
    User userLogin(String telPhone, String userPassword, HttpServletRequest request);

    /**
     * 获取脱敏用户信息
     * @param originUser 原始用户信息
     * @return 脱敏用户信息
     */
    User getSafeUser(User originUser);
}
