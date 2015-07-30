package com.ozstrategy.service;

import com.ozstrategy.dao.BaseDaoTestCase;
import org.junit.Test;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by lihao1 on 7/14/15.
 */
public class CronTest extends BaseManagerTestCase {
    @Autowired
    private SchedulerFactoryBean scheduler;
    @Test
    public void testS(){
        System.out.println("sss");
    }
    @Test
    public void testCron()throws Exception{
        Scheduler qs= scheduler.getScheduler();
        JobDetail job = JobBuilder.newJob(JobTest.class).withIdentity(new JobKey("job1", "group1")).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey("trigger1", "group1")).startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();
        qs.scheduleJob(job, trigger);

        // Start up the scheduler
        qs.start();

        //当前主线程睡眠2秒
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(30*1000);

        // shut down the scheduler
        qs.shutdown(true);



    }
}
