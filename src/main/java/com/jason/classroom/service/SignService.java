package com.jason.classroom.service;

import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Sign;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-08
 */
public interface SignService extends IService<Sign> {

    public Result signIn(HttpServletRequest request);

    public Result signCount(HttpServletRequest request);

    public void updateSignTask();

}
