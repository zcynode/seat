package com.jason.classroom.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.classroom.common.dto.LoginDTO;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Admin;
import com.jason.classroom.entity.User;
import com.jason.classroom.service.UserService;
import com.jason.classroom.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-03
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"用户接口"})
public class UserController {

    @Autowired
    private UserService userService;

//    @RequiresAuthentication
    @GetMapping("/index")
    @ApiOperation(value = "用户登录接口")
    public Result index(){
        User user = userService.getById(1);
        return Result.succ(user);
    }

    /**
     * 用户注册接口
     * @param user
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名/账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "school", value = "所属学校", required = true)
    })
    public Result save(@Validated @RequestBody User user) {
        return userService.regist(user);
    }

    /**
     * 用户登录接口
     * @param loginDTO
     * @param response
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名/账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
//        return userService.login(loginDTO,response);

        User user = userService.login(loginDTO, response);
        if(user==null){
            return Result.fail("账号或密码错误");
        }
        request.getSession().setAttribute("user",user);
        return Result.succ(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("school",user.getSchool())
                .map()
        );

    }

    /**
     * 用户注销登录接口
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/logout")
    @ApiOperation(value = "注销")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    /**
     * 获取用户信息
     * @return
     */
//    @RequiresAuthentication
    @GetMapping("/userinfo")
    public User getUserInfo(HttpServletRequest request){
        return userService.userinfo(request);
    }


    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PostMapping("chuserInfo")
    public Result chUserInfo(User user){
        return userService.chUserInfo(user);
    }

    /**
     * 获取用户名用于显示登录状态
     * @param request
     * @return
     */
    @GetMapping("/getusername")
    public Object getUserName(HttpServletRequest request){
        Object user = request.getSession().getAttribute("user");
        return user;
    }
}
