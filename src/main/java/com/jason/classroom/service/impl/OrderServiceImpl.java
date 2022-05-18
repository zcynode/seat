package com.jason.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.common.vo.OrderVO;
import com.jason.classroom.entity.*;
import com.jason.classroom.mapper.OrderMapper;
import com.jason.classroom.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-26
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Resource
    private OrderMapper orderMapper;

    @Override
    public Map<String,Object> getOrdersByUsername(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        List<Map<String, Object>> orders = orderMapper.selectMaps(new QueryWrapper<Order>().eq("username", user.getUsername()));
        List<Map<String, Object>> maps = new ArrayList<>();

        orders.forEach(item->{
            Timestamp starttimest = (Timestamp) item.get("starttime");
            LocalDateTime starttime = starttimest.toLocalDateTime();
            Timestamp endtimest = (Timestamp) item.get("endtime");
            LocalDateTime endtime = endtimest.toLocalDateTime();
            String date =starttime.getYear() +"年"+ starttime.getMonth() +"月" + starttime.getDayOfMonth()+"日";
            HashMap<String,Object> vo = new HashMap<>();
            vo.put("date",date);
            vo.put("starttime",starttime.getHour() +"时"+ starttime.getMinute() +"分");
            vo.put("endtime",endtime.getHour() +"时"+ endtime.getMinute() +"分");
            vo.put("num",item.get("num"));
            vo.put("room",item.get("room"));
            log.info("=============tablenum:" + item.get("tablenum"));
            String tablenum = item.get("tablenum").toString();
            vo.put("tablenum",tablenum);
            log.info("==============vo.puttablenum:" + vo.get("tablenum"));

            maps.add(vo);
        });
        map.put("data",maps);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }


    @Override
    public Result makeOrder(OrderVO orderVO, HttpServletRequest request) {
        Order order = new Order();
        //获取当前用户
        User user = (User) request.getSession().getAttribute("user");

        //时间拼接
        String date = orderVO.getDate();

        String starttimevo = orderVO.getStarttime();
        String starthour = starttimevo.substring(0, starttimevo.indexOf("点"));
        if (starthour.length()==1){
            starthour = "0" + starthour;
        }
        String startminute = starttimevo.substring(starttimevo.indexOf("点") + 1 , starttimevo.indexOf("分"));
        if (startminute.length()==1){
            startminute = "0" + startminute;
        }
        String starttimest = date + " " + starthour + ":" + startminute + ":00";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String endtimevo = orderVO.getEndtime();
        String endhour = endtimevo.substring(0, endtimevo.indexOf("点"));
        if (endhour.length()==1){
            endhour = "0" + endhour;
        }
        String endminute = endtimevo.substring(endtimevo.indexOf("点") + 1 , endtimevo.indexOf("分"));
        if (endminute.length()==1){
            endminute = "0" + endminute;
        }
        String endtimest = date + " " + endhour + ":" + endminute + ":00";

        LocalDateTime starttime = LocalDateTime.parse(starttimest, fmt);
        LocalDateTime endtime = LocalDateTime.parse(endtimest, fmt);

        order.setStarttime(starttime);
        order.setEndtime(endtime);
        if (isAble(orderVO.getRoom(),starttime,endtime)){
            order.setRoom(orderVO.getRoom());
        }else {
            return Result.fail("该教室已被占用");
        }

        order.setTablenum(orderVO.getX() + "排" + orderVO.getY() + "列");
        order.setSchool(user.getSchool());
        order.setNum(String.valueOf(Math.round((Math.random()+1) * 1000)));
        order.setUsername(user.getUsername());
        orderMapper.insert(order);


        return Result.succ("订座成功");
    }

    public Boolean isAble(String roomnum, LocalDateTime starttime, LocalDateTime endtime){
        return true;

    }

    @Override
    public Map<String, Object> orderList() {
        Map<String,Object> map = new HashMap<>();
//        List<User> users = adminMapper.selectAll();
        List<Map<String, Object>> orders = orderMapper.selectMaps(new QueryWrapper<Order>());
        map.put("data",orders);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }
}
