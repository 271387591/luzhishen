package com.ozstrategy.service;

import com.ozstrategy.model.money.SystemRmb;
import com.ozstrategy.model.money.UserApply;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lihao1 on 5/28/15.
 */
public class ApplyNoInstance {
    private volatile static ApplyNoInstance instance=null;
    private static String date ;
    private ApplyNoInstance(){}
    public static ApplyNoInstance getInstance(){
        if(instance==null){
            synchronized (ApplyNoInstance.class){
                if(instance==null){
                    instance=new ApplyNoInstance();
                }
            }
        }
        return instance;
    }
    public synchronized static String applyNo(UserApply apply){
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());
        if(date==null||!date.equals(str)){
            date = str;
        }
        Long orderNo = Long.parseLong((date)) * 10000;
        orderNo += apply.getId();;
        return orderNo+"";
    }

}
