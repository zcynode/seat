package com.jason.classroom.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.classroom.common.dto.LoginDTO;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Admin;
import com.jason.classroom.entity.Teacher;
import com.jason.classroom.entity.User;
import com.jason.classroom.mapper.TeacherMapper;
import com.jason.classroom.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jason.classroom.util.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-09
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private JwtUtils jwtUtils;

    @Override
    public Teacher login(LoginDTO loginDTO, HttpServletResponse response) {
        //根据用户名查老师
        Teacher teacher = this.getOne(new QueryWrapper<Teacher>().eq("username", loginDTO.getUsername()));

//        Assert.notNull(teacher,"该教师用户不存在");
        if (teacher == null){
            return null;
        }
        if (!teacher.getPassword().equals(loginDTO.getPassword())){
            return null;
        }
        String jwt = jwtUtils.generateToken(teacher.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return teacher;
    }


    @Override
    public Result chInfo(Teacher teacher) {

        this.update(teacher, new QueryWrapper<Teacher>().eq("username",teacher.getUsername()));

        return Result.succ("信息修改成功");
    }

    @Override
    public Result regist(Teacher teacher) {
        //查询数据库中是否存在该用户
        if (teacherMapper.selectCount(new QueryWrapper<Teacher>().eq("username", teacher.getUsername())) == 1){
            return Result.fail("该教师用户已存在");
        }

        //如果不存在则创建
        //密码加密
        String md5 = SecureUtil.md5(teacher.getPassword());
        teacher.setPassword(md5);
        this.save(teacher);
        return Result.succ(200,"注册成功",MapUtil.builder()
                .put("id",teacher.getId())
                .put("username",teacher.getUsername())
                .put("phone",teacher.getPhone())
                .put("school",teacher.getSchool())
                .map());
    }


    @Override
    public Teacher teacherInfo(HttpServletRequest request) {
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        if (StringUtils.isEmpty(teacher.getUsername())){
            return null;
        }else {
            Teacher newteacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("username", teacher.getUsername()));
            return newteacher;
        }
    }
}
