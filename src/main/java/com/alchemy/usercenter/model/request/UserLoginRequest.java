package com.alchemy.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author KunCheng He
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 319281716373120683L;

    /**
     * 用户电话号码
     */
    private String  telPhone;

    /**
     * 用户密码
     */
    private String userPassword;

}
