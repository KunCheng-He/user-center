package com.alchemy.usercenter.mapper;

import com.alchemy.usercenter.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author KunCheng He
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-08-18 15:24:00
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




