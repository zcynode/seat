package com.jason.classroom.service;

import com.jason.classroom.common.dto.LoginDTO;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jason.classroom.entity.Room;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-04
 */
public interface AdminService extends IService<Admin> {

    public Admin login(LoginDTO loginDTO, HttpServletResponse response);

    public Map<String,Object> userList();

    public Map<String,Object> roomList();

    public void deleteRoom(String roomnum);

    public Result addRoom(Room room);

    public void editRoom(String num, Integer capacity, String school);

    public Result adminInfo(String username);

}
