package com.alchemy.usercenter.controller;

import cn.hutool.core.util.StrUtil;
import com.alchemy.usercenter.model.User;
import com.alchemy.usercenter.model.request.UserLoginRequest;
import com.alchemy.usercenter.model.request.UserRegisterRequest;
import com.alchemy.usercenter.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.alchemy.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.alchemy.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author KunCheng He
 */
@RestController
@RequestMapping("/user")
@Slf4j
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

    @GetMapping("/search")
    public List<User> searchUsers(String telPhone, String userName, HttpServletRequest request) {
        // 鉴权
        if (!isAdmin(request)) {
            log.info("非管理员无查询权限");
            return new ArrayList<>();
        }
        // 查询 结果脱敏
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(userName))
            wrapper.like("name", userName);
        if (StrUtil.isNotBlank(telPhone))
            wrapper.eq("tel_phone", telPhone);
        return userService.list(wrapper).stream().map(user -> userService.getSafeUser(user)).collect(Collectors.toList());
    }

    @PostMapping("delete")
    public Boolean deleteUser(@RequestBody Long id, HttpServletRequest request) {
        // 鉴权
        if (!isAdmin(request)) {
            log.info("非管理员无删除权限");
            return false;
        }
        // 删除
        if (id < 0)
            return false;
        return userService.removeById(id);
    }

    /**
     * 判断是否为管理员
     *
     * @param request 请求
     * @return 是否为管理员
     */
    private Boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
