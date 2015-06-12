package com.ozstrategy.orm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lihao
 * Date: 7/17/13
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 * 简单的单表搜索查询工具
 */
public class QuerySearch {
    public static final Log logger = LogFactory.getLog(QuerySearch.class);
    private List<QueryField> commands=new ArrayList<QueryField>();
    private List<QueryField> orCommands=new ArrayList<QueryField>();
    private Map<String,Object> map;
    private Map<String,Object> namedMap = new HashMap<String, Object>();
    private Map<String,String> sortMap = new HashMap<String, String>();
    private String hql;



    public QuerySearch(String baseHql,Map<String,Object> map){
        this.hql=baseHql;
        this.map=map;
    }
    public QuerySearch addSort(String sort,String dir){
        if(StringUtils.isNotEmpty(sort)){
            if(StringUtils.isNotEmpty(dir)){
                this.sortMap.put(sort,dir);
            }else{
                this.sortMap.put(sort,"ASC");
            }
        }
        return this;
    }
    public Query pageQuery(Session session){
        return query(session,true);
    }
    public Query query(Session session){
        return query(session,false);
    }

    private Query query(Session session,boolean page){
        this.initField();
        String hql=hql();
        if(sortMap.size()>0){
            hql+=" order by ";
            for(Iterator<Map.Entry<String,String>> it=sortMap.entrySet().iterator();it.hasNext();){
                Map.Entry<String,String> entry=it.next();
                String key=entry.getKey();
                String value=entry.getValue();
                hql+= key+" "+value+",";
            }
            hql=hql.substring(0,hql.length()-1);
        }
        Query query =session.createQuery(hql);
        query=query(query,namedMap);
        if(page){
            Integer start=Integer.parseInt(map.get("start").toString());
            Integer limit=Integer.parseInt(map.get("limit").toString());
            query.setFirstResult(start).setMaxResults(limit);
        }
        return query;
    }


    private String hql(){
        String add_baseHql="",or_baseHql="";
        for(QueryField field:commands){
            String getHql=field.getPartHql();
            add_baseHql+=getHql;
        }
        for(QueryField field:orCommands){
            String getHql=field.getPartOrHql();
            or_baseHql+=getHql;
        }
        if(or_baseHql!=""){
            or_baseHql=or_baseHql.replaceFirst("or","");
            or_baseHql=" and ("+or_baseHql+")";
        }

        hql+=add_baseHql;
        hql+=or_baseHql;
        return hql;
    }
    public static void main(String[] args){
        Map<String,Object> map1=new HashMap<String, Object>();
        map1.put("Q_name_LK","d");
        map1.put("Q_name1_LK","d");
        map1.put("Q_name2_LK_OR","d");
        map1.put("Q_name3_LK_OR","d");
        QuerySearch search=new QuerySearch("from user where 1=1",map1);
        search.initField();
        String hql=search.hql();
        System.out.println(hql);

    }
    private void initField(){
        if(map!=null){
            for(Iterator<Map.Entry<String,Object>> it=map.entrySet().iterator();it.hasNext();){
                Map.Entry<String,Object> entry=it.next();
                String key=entry.getKey();
                Object value=entry.getValue();
                addFilter(key,value);
            }
        }
    }
    private Query query(Query query,Map<String,Object> map){
        String[] names=query.getNamedParameters();
        if(names!=null){
            for(String key:names){
                Object value=map.get(key);
                if(key.contains("LK")){
                    value="%"+value+"%";
                }else if(key.contains("LLK")){
                    value="%"+value;
                }else if(key.contains("RLK")){
                    value=value+"%";
                }else if(key.contains("NULL")){
                    continue;
                }else if(key.contains("NOTNULL")){
                    continue;
                }
                if(value!=null){
                    query.setParameter(key,value);
                }
            }
        }
        return query;
    }
    private void addFilter(String property, Object value) {
        if(value==null || StringUtils.isEmpty(value.toString())){
            return;
        }
        String as[] = property.split("[_]");
        /*Q_title_LK_L*/
        /*Q_u.title_LK_L*/
        if (as != null && as.length == 4) {
            String alias=property.replaceAll("\\.", "_");
            if(namedMap.containsKey(alias)){
                alias=alias+"_1";
            }
            value=convertObject(as[3],value.toString());
            namedMap.put(alias,value);
            QueryField queryField = new QueryField(alias, as[1], as[2]);
            commands.add(queryField);
        }else if(as != null && as.length == 5){/*Q_title_LK_OR_L*/
            String alias=property.replaceAll("\\.", "_");
            if(namedMap.containsKey(alias)){
                alias=alias+"_1";
            }
            value=convertObject(as[4],value.toString());
            namedMap.put(alias,value);
            QueryField queryField = new QueryField(alias, as[1], as[2]);
            orCommands.add(queryField);
        }
    }
    private static Object convertObject(String s, String s1){
        if (StringUtils.isEmpty(s1))
            return null;
        Object obj = null;
        try{
            if ("S".equals(s))
                obj = s1;
            else if ("L".equals(s))
                obj = new Long(s1);
            else if ("N".equals(s))
                obj = new Integer(s1);
            else if ("BD".equals(s))
                obj = new BigDecimal(s1);
            else if ("FT".equals(s))
                obj = new Float(s1);
            else if ("SN".equals(s))
                obj = new Short(s1);
            else if ("BT".equals(s))
                obj=new Byte(s1);
            else if ("DB".equals(s))
                obj=new Double(s1);
            else if ("BL".equals(s))
                obj= new Boolean(s1);
            else if ("D".equals(s))
                obj = DateUtils.parseDate(s1, new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
            else {
                obj = s1;
            }
        }catch (Exception exception){
        }
        return obj;
    }


}
