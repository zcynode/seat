package com.jason.classroom.controller;


import cn.hutool.core.map.MapUtil;
import com.jason.classroom.common.dto.LoginDTO;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Admin;
import com.jason.classroom.entity.Room;
import com.jason.classroom.service.AdminService;
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
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-04
 */
@RestController
@RequestMapping("/admin")
@Api(tags = {"管理员接口"})
public class AdminController {


    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录接口
     * @param loginDTO
     * @param response
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "管理员登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "管理员账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    public Result login(@Validated @RequestBody LoginDTO loginDTO,HttpServletRequest request, HttpServletResponse response){
        Admin admin = adminService.login(loginDTO, response);
        if(admin==null){
            return Result.fail("账号或密码错误");
        }
        request.getSession().setAttribute("admin",admin);
        return Result.succ(MapUtil.builder()
                .put("id",admin.getId())
                .put("username",admin.getUsername())
                .map()
        );
    }

    /**
     * 管理员注销登录接口
     * @return
     */
//    @RequiresAuthentication
    @GetMapping("/logout")
    @ApiOperation(value = "管理员注销接口")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    @GetMapping("/adminInfo")
    @ApiOperation(value = "获取管理员信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "管理员账号", required = true)
    })
    public Result adminInfo(String username){
        return adminService.adminInfo(username);
    }


    @GetMapping("/getadminname")
    @ApiOperation(value = "获取管理员用户名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "session", required = true)
    })
    public Object getAdminName(HttpServletRequest request){
        Object admin = request.getSession().getAttribute("admin");
        return admin;
    }

    /**
     * 获取全部用户列表
     * @return
     */
    @GetMapping("/userlist")
    @ApiOperation(value = "获取用户信息列表")
    public Map<String,Object> userList(){
        return adminService.userList();
    }

    /**
     * 获取全部教室列表
     * @return
     */
//    @RequiresAuthentication
    @GetMapping("/roomlist")
    @ApiOperation(value = "获取教室列表")
    public Map<String,Object> roomList(){
        return adminService.roomList();
    }


    /**
     * 删除自习室
     * @param roomnum
     */
//    @RequiresAuthentication
    @PostMapping("/deleteRoom")
    @ApiOperation(value = "删除自习室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "自习室编号", required = true)
    })
    public void deleteRoom(@RequestParam("num")String roomnum){
        adminService.deleteRoom(roomnum);
    }

//    @PostMapping("addTable")
//    public void addTable(@RequestParam("num")String num,
//                         @RequestParam("capacity")Integer capacity,
//                         @RequestParam("school")String school){
//
//
//    }

//    @RequiresAuthentication
    @PostMapping("/addRoom")
    @ApiOperation(value = "添加自习室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "room", value = "自习室信息", required = true)
    })
    public Result addRoom(Room room){
        return adminService.addRoom(room);
    }

//    @RequiresAuthentication
    @PostMapping("/editRoom")
    @ApiOperation(value = "编辑自习室信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "capacity", value = "容量", required = true),
            @ApiImplicitParam(name = "school", value = "学校", required = true),
            @ApiImplicitParam(name = "num", value = "教室号", required = true),
    })
    public void editRoom(@RequestParam("capacity")Integer capacity,
                         @RequestParam("school")String school,
                         @RequestParam("num")String num){
        adminService.editRoom(num,capacity,school);
    }

}
