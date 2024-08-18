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
}