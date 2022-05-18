package com.jason.classroom.controller;


import com.jason.classroom.common.lang.Result;
import com.jason.classroom.common.vo.CourseVO;
import com.jason.classroom.common.vo.OrderVO;
import com.jason.classroom.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-26
 */
@RestController
@RequestMapping("/order")
@Api(tags = {"预约接口"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrders")
    public Map<String,Object> getOrdersByUsername(HttpServletRequest request){
        return orderService.getOrdersByUsername(request);
    }

    @PostMapping("/makeorder")
    public Result makeOrder(OrderVO orderVO, HttpServletRequest request){
        return orderService.makeOrder(orderVO,request);
    }

    @GetMapping("/orderList")
    public Map<String,Object> orderList(){
        return orderService.orderList();
    }
}
