package com.jason.classroom.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.classroom.common.dto.LoginDTO;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.User;
import com.jason.classroom.mapper.UserMapper;
import com.jason.classroom.service.UserService;
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
 * @since 2021-05-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtils jwtUtils;


    @Override
    public User login(LoginDTO loginDTO, HttpServletResponse response) {
        User user = this.getOne(new QueryWrapper<User>().eq("username", loginDTO.getUsername()));

//        Assert.notNull(user,"用户不存在");
        if (user == null){
            return null;
        }
        if (!user.getPassword().equals(SecureUtil.md5(loginDTO.getPassword()))){
            return null;
        }
        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return user;
    }

    @Override
    public Result regist(User user) {
        //查询数据库中是否存在该用户
        if (userMapper.selectCount(new QueryWrapper<User>().eq("username", user.getUsername())) == 1){
            return Result.fail("该用户已存在");
        }

        //如果不存在则创建
        user.setStatus(1);
        //密码加密
        String md5 = SecureUtil.md5(user.getPassword());
        user.setPassword(md5);
        this.save(user);
        return Result.succ(200,"注册成功",MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("phone",user.getPhone())
                .put("school",user.getSchool())
                .map());
    }


    @Override
    public User userinfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(user.getUsername())){
            return null;
        }else {
            User newuser = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));
            return newuser;
        }

    }

    @Override
    public Result chUserInfo(User user) {
        if (user ==null){
            log.error("传入修改内容为空");
        }

        User selectOne = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername()));


        //判断密码是否改变，如果改变应进行加密
        if (!selectOne.getPassword().equals(SecureUtil.md5(user.getPassword()))){
            String md5 = SecureUtil.md5(user.getPassword());
            user.setPassword(md5);
        }

        userMapper.update(user, new QueryWrapper<User>().eq("username",user.getUsername()));

        return Result.succ(200,"用户信息修改成功",MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("password",user.getPassword())
                .put("email",user.getEmail())
                .put("phone",user.getPhone())
                .put("school",user.getSchool())
                .map());
    }
}
