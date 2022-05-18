package com.jason.classroom.service;

import com.jason.classroom.common.lang.Result;
import com.jason.classroom.common.vo.OrderVO;
import com.jason.classroom.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-26
 */
public interface OrderService extends IService<Order> {

    public Map<String,Object> getOrdersByUsername(HttpServletRequest request);

    public Result makeOrder(OrderVO orderVO, HttpServletRequest request);

    public Map<String,Object> orderList();

}
