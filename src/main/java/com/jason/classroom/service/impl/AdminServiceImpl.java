package com.jason.classroom.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.classroom.common.dto.LoginDTO;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Admin;
import com.jason.classroom.entity.Room;
import com.jason.classroom.entity.User;
import com.jason.classroom.mapper.AdminMapper;
import com.jason.classroom.mapper.RoomMapper;
import com.jason.classroom.mapper.UserMapper;
import com.jason.classroom.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jason.classroom.service.RoomService;
import com.jason.classroom.util.ChineseUtils;
import com.jason.classroom.util.JwtUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-04
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoomService roomService;


    @Override
    public Admin login(LoginDTO loginDTO, HttpServletResponse response) {
        Admin admin = this.getOne(new QueryWrapper<Admin>().eq("username", loginDTO.getUsername()));

        Assert.notNull(admin,"管理员不存在");
        if (!admin.getPassword().equals(loginDTO.getPassword())){
            return null;
        }
        String jwt = jwtUtils.generateToken(admin.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return admin;
//        return Result.succ(MapUtil.builder()
//                .put("id",admin.getId())
//                .put("username",admin.getUsername())
//                .map()
//        );
    }

    @Override
    public Map<String,Object> userList() {
        Map<String,Object> map = new HashMap<>();
//        List<User> users = adminMapper.selectAll();
        List<Map<String, Object>> users = userMapper.selectMaps(new QueryWrapper<User>());
        map.put("data",users);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }

    @Override
    public Map<String,Object> roomList() {
//        List<Room> rooms = roomMapper.selectAll();

        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> rooms = roomMapper.selectMaps(new QueryWrapper<Room>());
        map.put("data",rooms);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }

    @Override
    public void deleteRoom(String roomnum) {
        roomMapper.delete(new QueryWrapper<Room>().eq("num", roomnum));
    }

    @Override
    public Result addRoom(Room room) {
        //设置默认状态
        room.setStatus(1);
        //生成一个对应的自习室编号
        String school = room.getSchool();
        String firstnum = ChineseUtils.convertHanzi2Pinyin(school, false);
        String lastnum = String.valueOf(roomMapper.selectMaxId()+1);

        String num = firstnum + lastnum;
        room.setNum(num);

        roomMapper.insert(room);

        return Result.succ("添加成功");
    }

    @Override
    public void editRoom(String num, Integer capacity, String school) {

        Room selectOne = roomMapper.selectOne(new QueryWrapper<Room>().eq("num", num));
        selectOne.setCapacity(capacity);
        selectOne.setSchool(school);

        String firstnum = ChineseUtils.convertHanzi2Pinyin(school, false);
        String lastnum = String.valueOf(roomMapper.selectMaxId()+1);
        String numchange = firstnum + lastnum;

        selectOne.setNum(numchange);

        roomMapper.updateById(selectOne);

    }

    @Override
    public Result adminInfo(String username) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
        return Result.succ(MapUtil.builder()
                .put("id",admin.getId())
                .put("username",admin.getUsername())
                .map()
        );
    }
}
