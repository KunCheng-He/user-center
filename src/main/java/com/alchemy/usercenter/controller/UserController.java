package com.alchemy.usercenter.controller;

import cn.hutool.core.util.StrUtil;
import com.alchemy.usercenter.model.User;
import com.alchemy.usercenter.model.request.UserLoginRequest;
import com.alchemy.usercenter.model.request.UserRegisterRequest;
import com.alchemy.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author KunCheng He
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 校验
        if (userRegisterRequest == null) {
            return null;
        }
        String telPhone = userRegisterRequest.getTelPhone();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StrUtil.hasBlank(telPhone, userPassword, checkPassword))
            return null;

        // 调用服务层
        return userService.userRegister(telPhone, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userRegisterRequest, HttpServletRequest request) {
        // 校验
        if (userRegisterRequest == null) {
            return null;
        }
        String telPhone = userRegisterRequest.getTelPhone();
        String userPassword = userRegisterRequest.getUserPassword();
        if (StrUtil.hasBlank(telPhone, userPassword))
            return null;

        // 调用服务层
        return userService.userLogin(telPhone, userPassword, request);
    }
}
