package com.jason.classroom.service;

import com.jason.classroom.common.lang.Result;
import com.jason.classroom.entity.Table;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason Wang
 * @since 2021-05-03
 */
public interface TableService extends IService<Table> {

    public Result table4List(String room);

    public Result orderTable(Table table);

    public Result getAbleTable(String tablenum);

}
