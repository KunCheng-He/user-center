package com.alchemy.usercenter.service.impl;

import com.alchemy.usercenter.model.User;
import com.alchemy.usercenter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 *
 * @author KunCheng He
 */
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setName("hello");
        user.setPassword("123456");
        user.setTelPhone("123456");
        user.setEmail("123456@qq.com");
        user.setAvatar("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.aigei.com%2Fset%2Fbianpinghuatouxiangt_2.html%3Fpage%3D2&psig=AOvVaw0zGuYnKyIeYPWu9_xCIgHn&ust=1724053541512000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCLiU3oqG_ocDFQAAAAAdAAAAABAE");
        user.setGender(0);

        boolean result = userService.save(user);
        assertTrue(result);
    }

    @Test
    void userRegister() {
        // 测试空参
        System.out.println(userService.userRegister(null, null, null));
        String telPhone = "110111164929";
        System.out.println(userService.userRegister(telPhone, null, null));
        String userPassword = "123456";
        System.out.println(userService.userRegister(telPhone, userPassword, null));
        String checkPassword = "123456";

        // 校验手机号错误
        System.out.println(userService.userRegister(telPhone, userPassword, checkPassword));

        // 密码长度不够
        telPhone = "11011092759";
        System.out.println(userService.userRegister(telPhone, userPassword, checkPassword));

        // 校验密码一致性
        userPassword = "123456abcd@";
        checkPassword = "123456abcd";
        System.out.println(userService.userRegister(telPhone, userPassword, checkPassword));

        // 校验密码是否合法
        userPassword = "123456abcd🌟";
        checkPassword = "123456abcd🌟";
        System.out.println(userService.userRegister(telPhone, userPassword, checkPassword));

        // 注册用户(第一次运行成功，第二次因为用户已注册要运行失败)
        userPassword = "123456abcd";
        checkPassword = "123456abcd";
        System.out.println(userService.userRegister(telPhone, userPassword, checkPassword));
    }
}