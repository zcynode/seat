package com.jason.classroom.mapper;

import com.jason.classroom.entity.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jason.classroom.entity.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-03
 */
public interface RoomMapper extends BaseMapper<Room> {

    public List<Room> selectAll();

    public int selectMaxId();

}
