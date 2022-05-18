package com.jason.classroom.schedule;

import com.jason.classroom.controller.SignController;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
public class UpdateScheduleTask {

    @Resource
    private SignController signController;

    @Scheduled(cron = "0 0 0 * * ?")
    private void updateSignTask(){
        signController.updateSignTask();
    }
}
