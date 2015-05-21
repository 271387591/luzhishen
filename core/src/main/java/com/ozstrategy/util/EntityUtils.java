package com.ozstrategy.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by lihao1 on 4/29/15.
 */
public class EntityUtils<T> {
    private T obj;
    public EntityUtils(T obj){
        this.obj=obj;
    }
    public T populate(Map<String,Object> map){
        try {
            BeanUtils.populate(obj,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
