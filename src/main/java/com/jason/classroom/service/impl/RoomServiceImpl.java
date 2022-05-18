package com.jason.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Room;
import com.jason.classroom.entity.Teacher;
import com.jason.classroom.entity.User;
import com.jason.classroom.mapper.RoomMapper;
import com.jason.classroom.service.RoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-03
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Resource
    private RoomMapper roomMapper;

    @Override
    public Result getRoomsBySchool(HttpServletRequest request) {
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        List<Room> rooms = new ArrayList<>();
        if (teacher != null){
            rooms = roomMapper.selectList(new QueryWrapper<Room>().eq("school", teacher.getSchool()));

        }else {
            User user = (User) request.getSession().getAttribute("user");
            rooms = roomMapper.selectList(new QueryWrapper<Room>().eq("school", user.getSchool()));
        }

        ArrayList<String> list = new ArrayList<>();
        rooms.forEach(item -> {
            list.add(item.getNum());
        });
        return Result.succ(list);
    }

    @Override
    public Result getRoomsByStatus(Integer status) {
        List<Room> rooms = roomMapper.selectList(new QueryWrapper<Room>().eq("status", status));
        return Result.succ(rooms);
    }

    @Override
    public Result Occupy(Room room) {

        //获取该自习室的占用时间
        LocalDateTime starttime = room.getStarttime();
        Room selectById = roomMapper.selectById(room.getId());
        LocalDateTime selectByIdEndtime = selectById.getEndtime();

        //判断该自习室在所选时间是否被占用
        if (starttime.isAfter(selectByIdEndtime)){
            room.setStatus(2);

            this.updateById(room);
            return Result.succ(room);
        }

        return Result.fail("该自习室在指定时段无法预约");
    }
}
