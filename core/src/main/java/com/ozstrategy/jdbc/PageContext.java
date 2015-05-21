package com.ozstrategy.jdbc;

public class PageContext{
        private String sql;
        private Integer start;
        private Integer limit;
        private String context;
        public PageContext(String sql,Integer start,Integer limit){
            this.start=start;
            this.limit=limit;
            this.sql=sql+" limit "+start+","+limit;
        }
        public String pageSql(){
            return sql;
        }
    }