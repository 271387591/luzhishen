package com.ozstrategy.service;

import com.ozstrategy.Constants;
import com.ozstrategy.model.order.CreditsOrder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lihao1 on 5/28/15.
 */
public class OrderNoInstance {
    private volatile static OrderNoInstance instance=null;
    private static String date ;
    private OrderNoInstance(){}
    public static OrderNoInstance getInstance(){
        if(instance==null){
            synchronized (OrderNoInstance.class){
                if(instance==null){
                    instance=new OrderNoInstance();
                }
            }
        }
        return instance;
    }
    public synchronized static String orderNo(CreditsOrder order){
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());
        if(date==null||!date.equals(str)){
            date = str;
        }
        Long orderNo = Long.parseLong((date)) * 10000;
        orderNo += order.getId();;
        return orderNo+"";
    }

}
