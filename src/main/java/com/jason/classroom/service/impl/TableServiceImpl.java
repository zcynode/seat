package com.jason.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Room;
import com.jason.classroom.entity.Table;
import com.jason.classroom.entity.User;
import com.jason.classroom.mapper.RoomMapper;
import com.jason.classroom.mapper.TableMapper;
import com.jason.classroom.service.TableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements TableService {

    @Resource
    private TableMapper tableMapper;
    @Resource
    private RoomMapper roomMapper;


    @Override
    public Result table4List(String room) {
        List<Table> tables = tableMapper.selectList(new QueryWrapper<Table>().eq("room", room));
        return Result.succ(tables);
    }

    @Override
    public Result orderTable(Table table) {
        //判断该自习室是否被整体占用
        Room room = roomMapper.selectOne(new QueryWrapper<Room>().eq("num", table.getRoom()));
        if (room.getStatus() != 1){
            return Result.fail("该自习室已被预定");
        }else {
            Table select = tableMapper.selectById(table.getId());
            if (select.getStatus() == 1){
                table.setStatus(2);
                tableMapper.updateById(table);

                return Result.succ("预定成功");
            }

            return Result.fail("不可预定");
        }
    }


    @Override
    public Result getAbleTable(String tablenum) {
        List<Table> tables = tableMapper.selectList(new QueryWrapper<Table>().eq("room", tablenum));
        ArrayList<String> list = new ArrayList<>();
        tables.forEach(item -> {
            list.add(item.getX() + "；" + item.getY());
        });
        return Result.succ(list);
    }
}
