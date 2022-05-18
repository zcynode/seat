package com.jason.classroom.mapper;

import com.jason.classroom.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jason.classroom.entity.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-04
 */
public interface AdminMapper extends BaseMapper<Admin> {

    public List<User> selectAll();

}
