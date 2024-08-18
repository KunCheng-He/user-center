package com.alchemy.usercenter.service;

import com.alchemy.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @return 注册是否成功
     */
    Long userRegister(String telPhone, String userPassword, String checkPassword);
}
