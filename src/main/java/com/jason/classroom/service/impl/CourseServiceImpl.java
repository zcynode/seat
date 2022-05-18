package com.jason.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jason.classroom.common.lang.Result;
import com.jason.classroom.common.vo.CourseVO;
import com.jason.classroom.entity.*;
import com.jason.classroom.mapper.CourseMapper;
import com.jason.classroom.mapper.RoomMapper;
import com.jason.classroom.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-29
 */
@Slf4j
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private RoomMapper roomMapper;


    @Override
    public Map<String, Object> getCourseList(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        List<Map<String, Object>> courses = courseMapper.selectMaps(new QueryWrapper<Course>().eq("teachername", teacher.getUsername()));
        List<Map<String, Object>> maps = new ArrayList<>();

        courses.forEach(item->{
            Timestamp starttimest = (Timestamp) item.get("starttime");
            LocalDateTime starttime = starttimest.toLocalDateTime();
            Timestamp endtimest = (Timestamp) item.get("endtime");
            LocalDateTime endtime = endtimest.toLocalDateTime();
            String date =starttime.getYear() +"年"+ starttime.getMonth() +"月" + starttime.getDayOfMonth()+"日";
            HashMap<String,Object> vo = new HashMap<>();
            vo.put("date",date);
            vo.put("starttime",starttime.getHour() +"时"+ starttime.getMinute() +"分");
            vo.put("endtime",endtime.getHour() +"时"+ endtime.getMinute() +"分");
            vo.put("room",item.get("room"));
            vo.put("coursename",item.get("coursename"));
            vo.put("announcement",item.get("announcement"));

            maps.add(vo);
        });
        map.put("data",maps);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }


    @Override
    public Result startCourse(CourseVO courseVO, HttpServletRequest request) {

        Course course = new Course();
        //获取当前用户
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");

        //时间拼接
        String date = courseVO.getDate();

        String starttimevo = courseVO.getStarttime();
        String starthour = starttimevo.substring(0, starttimevo.indexOf("点"));
        if (starthour.length()==1){
            starthour = "0" + starthour;
        }
        String startminute = starttimevo.substring(starttimevo.indexOf("点") + 1 , starttimevo.indexOf("分"));
        if (startminute.length()==1){
            startminute = "0" + startminute;
        }
        String starttimest = date + " " + starthour + ":" + startminute + ":00";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String endtimevo = courseVO.getEndtime();
        String endhour = endtimevo.substring(0, endtimevo.indexOf("点"));
        if (endhour.length()==1){
            endhour = "0" + endhour;
        }
        String endminute = endtimevo.substring(endtimevo.indexOf("点") + 1 , endtimevo.indexOf("分"));
        if (endminute.length()==1){
            endminute = "0" + endminute;
        }
        String endtimest = date + " " + endhour + ":" + endminute + ":00";

        LocalDateTime starttime = LocalDateTime.parse(starttimest, fmt);
        LocalDateTime endtime = LocalDateTime.parse(endtimest, fmt);

        course.setStarttime(starttime);
        course.setEndtime(endtime);
        if (isAble(courseVO.getRoom(),starttime,endtime)){
            course.setRoom(courseVO.getRoom());
        }else {
            return Result.fail("该教室已被占用");
        }

        course.setCoursename(courseVO.getCoursename());
        course.setAnnouncement(courseVO.getAnnouncement());
        course.setTeachername(teacher.getUsername());

        courseMapper.insert(course);
        //更新教室状态
        Room room = roomMapper.selectOne(new QueryWrapper<Room>().eq("num", course.getRoom()));
        room.setStarttime(starttime);
        room.setEndtime(endtime);
        roomMapper.updateById(room);

        return Result.succ("开课成功");
    }

    public Boolean isAble(String roomnum, LocalDateTime starttime, LocalDateTime endtime){
        Room room = roomMapper.selectOne(new QueryWrapper<Room>().eq("num", roomnum));
        LocalDateTime starttime1 = room.getStarttime();
        LocalDateTime endtime1 = room.getEndtime();
        if (room.getStatus() == 1){
            if (starttime1 != null){
                return (starttime.isAfter(endtime1) || endtime.isBefore(starttime1));
            }else {
                return true;
            }

        }else {
            return false;
        }

    }
}
