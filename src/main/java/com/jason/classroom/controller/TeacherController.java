package com.jason.classroom.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.classroom.common.dto.LoginDTO;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Teacher;
import com.jason.classroom.entity.User;
import com.jason.classroom.service.TeacherService;
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
 * @since 2021-05-09
 */
@RestController
@RequestMapping("/teacher")
@Api(tags = {"教师接口"})
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserController userController;

    /**
     * 教师登录接口
     * @param loginDTO
     * @param response
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "教师登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名/账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){
        Teacher teacher = teacherService.login(loginDTO, response);
        if(teacher==null){
            return Result.fail("账号或密码错误");
        }
        request.getSession().setAttribute("teacher",teacher);
        return Result.succ(MapUtil.builder()
                .put("id",teacher.getId())
                .put("username",teacher.getUsername())
                .put("school",teacher.getSchool())
                .map()
        );

    }

    /**
     * 教师注册接口
     * @param teacher
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "教师注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "教师用户名/账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "school", value = "所属学校", required = true)
    })
    public Result save(@Validated @RequestBody Teacher teacher) {
        return teacherService.regist(teacher);
    }

    /**
     * 教师注销登录接口
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    /**
     * 获取教师信息
     * @return
     */
//    @RequiresAuthentication
    @GetMapping("/teacherInfo")
    public Teacher getTeacherInfo(HttpServletRequest request){
        Teacher teacher = teacherService.teacherInfo(request);
        return teacher;
    }


    /**
     * 修改教师信息
     * @param teacher
     * @return
     */
    @PostMapping("/chTeacherInfo")
    public Result chInfo(Teacher teacher){
        return teacherService.chInfo(teacher);
    }

    /**
     * 获取用户名用于显示登录状态
     * @param request
     * @return
     */
    @GetMapping("/getteachername")
    @ApiOperation(value = "获取教师姓名")
    public Object getUserName(HttpServletRequest request){
        Object teacher = request.getSession().getAttribute("teacher");
        return teacher;
    }


}
