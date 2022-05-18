package com.jason.classroom.controller;


import com.jason.classroom.common.lang.Result;
import com.jason.classroom.service.SignService;
import com.jason.classroom.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-08
 */
@RestController
@RequestMapping("/sign")
@Api(tags = {"签到接口"})
public class SignController {

    @Autowired
    private SignService signService;
    
    @GetMapping("/signin")
    public Result signIn(HttpServletRequest request){
        return signService.signIn(request);
    }

    @GetMapping("/signInfo")
    public Result signInfo(HttpServletRequest request){
        return signService.signCount(request);
    }

    public void updateSignTask(){
        signService.updateSignTask();
    }


}
