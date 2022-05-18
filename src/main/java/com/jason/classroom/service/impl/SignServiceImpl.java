package com.jason.classroom.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Sign;
import com.jason.classroom.entity.User;
import com.jason.classroom.mapper.SignMapper;
import com.jason.classroom.service.SignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-08
 */
@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements SignService {


    @Resource
    private SignMapper signMapper;

    @Override
    public Result signIn(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Sign selectOne = signMapper.selectOne(new QueryWrapper<Sign>().eq("username", user.getUsername()));



        if (selectOne != null){
            if (selectOne.getSigned() == 1){
                return Result.succ(201,"今天已经签到过了",null);
            }else {
                selectOne.setSigned(1);
                selectOne.setSigncount(selectOne.getSigncount() + 1);
                signMapper.updateById(selectOne);
                return Result.succ(200,"打卡成功",MapUtil.builder()
                        .put("count",selectOne.getSigncount())
                        .put("signed",1)
                        .map()
                );
            }
        }else {
            Sign sign = new Sign();
            sign.setUsername(user.getUsername());
            sign.setSigned(1);
            sign.setSigncount(1);
            signMapper.insert(sign);
            return Result.succ(200,"打卡成功",MapUtil.builder()
                    .put("count",1)
                    .put("signed",1)
                    .map()
            );
        }
    }


    @Override
    public Result signCount(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Sign selectOne = signMapper.selectOne(new QueryWrapper<Sign>().eq("username", user.getUsername()));
        if (selectOne != null){
            return Result.succ(MapUtil.builder()
                    .put("count",selectOne.getSigncount())
                    .put("signed",selectOne.getSigned())
                    .map()
            );
        }else {
            return Result.succ(400,"无签到信息",null);
        }
    }

    @Override
    public void updateSignTask() {
        this.update(new QueryWrapper<Sign>().eq("signed",0));
    }
}
