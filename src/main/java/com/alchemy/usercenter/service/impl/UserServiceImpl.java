package com.alchemy.usercenter.service.impl;

import com.alchemy.usercenter.mapper.UserMapper;
import com.alchemy.usercenter.model.User;
import com.alchemy.usercenter.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author KunCheng He
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-08-18 15:24:00
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
