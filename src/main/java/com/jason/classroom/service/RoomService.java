package com.jason.classroom.service;

import cn.hutool.http.HttpRequest;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-03
 */
public interface RoomService extends IService<Room> {

    public Result getRoomsBySchool(HttpServletRequest request);

    public Result getRoomsByStatus(Integer status);

    public Result Occupy(Room room);
}
