package com.alchemy.usercenter.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * user id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * user name
     */
    private String name;

    /**
     * user password
     */
    private String password;

    /**
     * user telephone
     */
    private String telPhone;

    /**
     * user email
     */
    private String email;

    /**
     * user avatar
     */
    private String avatar;

    /**
     * user gender: 0-male, 1-female
     */
    private Integer gender;

    /**
     * user status: 0-normal
     */
    private Integer status;

    /**
     * create time
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;

    /**
     * is deleted: 0-not deleted
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}