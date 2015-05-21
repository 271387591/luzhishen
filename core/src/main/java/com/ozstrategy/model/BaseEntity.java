package com.ozstrategy.model;

import com.ozstrategy.annotations.Id;
import com.ozstrategy.annotations.Order;
import com.ozstrategy.annotations.Table;
import com.ozstrategy.annotations.Transient;
import com.ozstrategy.jdbc.PageContext;
import com.ozstrategy.jdbc.QueryField;
import com.ozstrategy.jdbc.SqlBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lihao1 on 4/29/15.
 */
public abstract class BaseEntity {
    private static transient Map<String, List<String>> columnMap = null;
    static {
        columnMap = new HashMap<String, List<String>>();
    }

    public BaseEntity() {
        caculationColumnList();
    }
    final public String selectAllSql(){
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("*");
        SqlBuilder.FROM(getTableName());
        String orderSql=getOrder();
        if(StringUtils.isEmpty(orderSql)){
            SqlBuilder.ORDER_BY(getIdColumn()+" DESC");
        }else{
            SqlBuilder.ORDER_BY(orderSql);
        }

        String sql=SqlBuilder.SQL();
        return sql;
    }
    final public Object[] selectPageCount(Map<String, Object> params){
        QueryField queryField=new QueryField(params);
        String condition=queryField.getCondition();
        Object[] args=queryField.getArgs();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("count(*)");
        SqlBuilder.FROM(getTableName());
        SqlBuilder.WHERE("1=1");
        if(StringUtils.isNotEmpty(condition)){
            SqlBuilder.WHERE(condition);
        }

        Object[] objects=new Object[2];
        objects[0]=SqlBuilder.SQL();
        objects[1]=args;
        return objects;
    }
    final public Object[] selectPageSql(Map<String, Object> params, Integer start, Integer limit){
        QueryField queryField=new QueryField(params);
        String condition=queryField.getCondition();
        Object[] args=queryField.getArgs();
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("*");
        SqlBuilder.FROM(getTableName());
        if(StringUtils.isNotEmpty(condition)){
            SqlBuilder.WHERE(condition);
        }
        String orderSql=getOrder();
        if(StringUtils.isEmpty(orderSql)){
            SqlBuilder.ORDER_BY(getIdColumn()+" DESC");
        }else{
            SqlBuilder.ORDER_BY(orderSql);
        }

        String sql=SqlBuilder.SQL();
        Object[] objects=new Object[2];
        objects[0]=new PageContext(sql,start,limit).pageSql();
        objects[1]=args;
        return objects;
    }

    final public Object[] insertSql(){
        String tableName=getTableName();
        List<String> list = columnMap.get(tableName);
        List<String> columnList=new ArrayList<String>();
        List<String> valueList=new ArrayList<String>();
        List<Object> columnValues=new ArrayList<Object>();
        for(String column:list){
            columnList.add(column);
            valueList.add("?");
            columnValues.add(getColumnValue(this,column));
        }
        String columnNames= StringUtils.join(columnList.iterator(), ",");
        String values=StringUtils.join(valueList.iterator(),",");
        SqlBuilder.BEGIN();
        SqlBuilder.INSERT_INTO(tableName);
        SqlBuilder.VALUES(columnNames, values);
        String sql= SqlBuilder.SQL();
        Object[] objects=new Object[2];
        objects[0]=sql;
        objects[1]=columnValues.toArray();
        return objects;
    }
    final public Object[] updateSql(){
        String tableName=getTableName();
        List<String> list = columnMap.get(tableName);
        String id=getIdColumn();
        List<String> columnList=new ArrayList<String>();
        List<Object> columnValues=new ArrayList<Object>();
        for(String column:list){
            columnList.add(column+"=?");
            columnValues.add(getColumnValue(this,column));
        }
        columnValues.add(getColumnValue(this,id));
        String columnNames=StringUtils.join(columnList.iterator(), ",");
        SqlBuilder.BEGIN();
        SqlBuilder.UPDATE(tableName);
        SqlBuilder.SET(columnNames);
        SqlBuilder.WHERE(id+"=?");
        String sql= SqlBuilder.SQL();
        Object[] objects=new Object[2];
        objects[0]=sql;
        objects[1]=columnValues.toArray();
        return objects;
    }
    final public Object[] deleteSql(){
        String id=getIdColumn();
        String tableName=getTableName();
        SqlBuilder.BEGIN();
        SqlBuilder.DELETE_FROM(tableName);
        SqlBuilder.WHERE(id+"=?");
        String sql= SqlBuilder.SQL();
        List<Object> columnValues=new ArrayList<Object>();
        columnValues.add(getColumnValue(this,id));
        Object[] objects=new Object[2];
        objects[0]=sql;
        objects[1]=columnValues.toArray();
        return objects;
    }
    final public String getOrder(){
        Field[] fields=this.getClass().getDeclaredFields();
        List<String> list=new ArrayList<String>();
        for(Field field:fields){
            if(field.isAnnotationPresent(Order.class)){
                Order order=field.getAnnotation(Order.class);
                list.add(field.getName()+" "+order.type());
            }
        }
        return StringUtils.join(list.iterator(),",");
    }

    final public String getTableName() {
        Table table = this.getClass().getAnnotation(Table.class);
        if (table == null) {
            return this.getClass().getSimpleName().toUpperCase();
        } else {
            return table.name();
        }
    }
    final public String getIdColumn(){
        Field[] fields=this.getClass().getDeclaredFields();
        for(Field field:fields){
            if(field.isAnnotationPresent(Id.class)){
                return field.getName();
            }
        }
        return "id";
    }
    final public Object getColumnValue(Object obj,String column){
        try {
            return FieldUtils.readDeclaredField(obj,column,true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    final public void setIdValue(Long id){

        try {
            FieldUtils.writeDeclaredField(this,getIdColumn(),id,true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    final private void caculationColumnList() {
        String table=getTableName();
        if (columnMap.containsKey(table)) {
            return;
        }
        Field[] fields=this.getClass().getDeclaredFields();
        List<String> list=new ArrayList<String>();
        for(Field field:fields){
            if(field.isAnnotationPresent(Id.class)){
                continue;
            }
            if(field.isAnnotationPresent(Transient.class)){
                continue;
            }
            list.add(field.getName());
        }
        columnMap.put(table, list);
    }
    


}
