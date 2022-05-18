package com.jason.classroom.controller;


import com.jason.classroom.common.lang.Result;
import com.jason.classroom.common.vo.TableVO;
import com.jason.classroom.entity.Table;
import com.jason.classroom.mapper.TableMapper;
import com.jason.classroom.service.TableService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/table")
@Api(tags = {"座位接口"})
public class TableController {

    @Autowired
    private TableService tableService;

    /**
     * 查询指定自习室中的所有座位信息
     * @param room
     * @return
     */
    @GetMapping("/gettables/{room}")
    public Result table4List(@PathVariable(name = "room")String room){
        return tableService.table4List(room);

    }

    /**
     * 订位置
     * @param tableVO
     * @return
     */
    @PostMapping("/ordertable")
    public Result orderTable(TableVO tableVO){
        Table table = new Table();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime starttime = LocalDateTime.parse(tableVO.getStarttime(), df);
        LocalDateTime endtime = LocalDateTime.parse(tableVO.getEndtime(), df);

        //参数设置
        table.setId(tableVO.getId());
        table.setStatus(tableVO.getStatus());
        table.setRoom(tableVO.getRoom());
        table.setStarttime(starttime);
        table.setEndtime(endtime);

        return tableService.orderTable(table);
    }


    @GetMapping("/getabletable")
    public Result getAbleTable(String tablenum){
        return tableService.getAbleTable(tablenum);

    }

}
