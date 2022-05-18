package com.jason.classroom.controller;


import com.jason.classroom.common.lang.Result;
import com.jason.classroom.common.vo.CourseVO;
import com.jason.classroom.service.CourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-29
 */
@RestController
@RequestMapping("/course")
@Api(tags = {"课程接口"})
public class CourseController {

    @Autowired
    private CourseService courseService;


    @GetMapping("/courselist")
    public Map<String,Object> getCourseList(HttpServletRequest request){
        return courseService.getCourseList(request);
    }


    @PostMapping("/startCourse")
    public Result startCourse(CourseVO courseVO, HttpServletRequest request){
        return courseService.startCourse(courseVO,request);
    }

}
