package com.ozstrategy.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by lihao1 on 7/14/15.
 */
public class JobTest implements Job{
    public void run(){
        System.out.println("ss");
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("ss");
    }
}
