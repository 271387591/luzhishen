package com.ozstrategy.service;

import com.ozstrategy.model.money.SystemRmb;
import com.ozstrategy.model.order.CreditsOrder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lihao1 on 5/28/15.
 */
public class BachNoInstance {
    private volatile static BachNoInstance instance=null;
    private static String date ;
    private BachNoInstance(){}
    public static BachNoInstance getInstance(){
        if(instance==null){
            synchronized (BachNoInstance.class){
                if(instance==null){
                    instance=new BachNoInstance();
                }
            }
        }
        return instance;
    }
    public synchronized static String bachNo(SystemRmb rmb){
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());
        if(date==null||!date.equals(str)){
            date = str;
        }
        Long orderNo = Long.parseLong((date)) * 100;
        orderNo += rmb.getId();;
        return orderNo+"";
    }

}
