package com.jason.classroom.service;

import com.jason.classroom.common.lang.Result;
import com.jason.classroom.common.vo.CourseVO;
import com.jason.classroom.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-29
 */
public interface CourseService extends IService<Course> {

    public Map<String,Object> getCourseList(HttpServletRequest request);

    public Result startCourse(CourseVO courseVO, HttpServletRequest request);

}
