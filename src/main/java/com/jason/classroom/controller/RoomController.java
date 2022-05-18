package com.jason.classroom.controller;


import com.jason.classroom.common.lang.Result;
import com.jason.classroom.common.vo.RoomVO;
import com.jason.classroom.entity.Room;
import com.jason.classroom.service.RoomService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-03
 */
@RestController
@RequestMapping("/room")
@Api(tags = {"教室接口"})
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * 获取同一学校中的所有自习室
     * @param request
     * @return
     */
//    @RequiresAuthentication
    @GetMapping("/getRoomsBySchool")
    public Result getRoomsBySchool(HttpServletRequest request){
        return roomService.getRoomsBySchool(request);
    }

    /**
     * 根据状态获取可用或不可用的自习室
     * @param status
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/getRoomsByStatus/{status}")
    public Result getRoomsByStatus(@PathVariable(name = "status") Integer status){
        return roomService.getRoomsByStatus(status);
    }

    @RequiresAuthentication
    @PostMapping("/Occupy")
    public Result Occupy(RoomVO roomVO){
        Room room = new Room();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime starttime = LocalDateTime.parse(roomVO.getStarttime(), df);
        LocalDateTime endtime = LocalDateTime.parse(roomVO.getEndtime(), df);

        //参数设置
        room.setId(roomVO.getId());
        room.setStatus(roomVO.getStatus());
        room.setSchool(roomVO.getSchool());
        room.setStarttime(starttime);
        room.setEndtime(endtime);

        return roomService.Occupy(room);
    }

}
