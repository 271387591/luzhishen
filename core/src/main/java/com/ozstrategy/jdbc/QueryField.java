package com.ozstrategy.jdbc;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QueryField {
    private String property;
    private String operation;
    private String condition = "1=1";
    private String alias;
    private List<Object> values = new ArrayList<Object>();

    public QueryField(Map<String, Object> map) {
        this(map, null);
    }

    public QueryField(Map<String, Object> map, String alias) {
        this.alias = alias;
        if (map != null) {
            for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Object> entry = it.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.startsWith("Q_")) {
                    if (value != null && StringUtils.isNotEmpty(ObjectUtils.toString(value))) {
                        String[] keys = key.split("_");
                        this.property = keys[1];
                        this.operation = keys[2];
                        this.condition += getPartHql();
                        if (operation.equals("LK")) {
                            this.values.add(value);
                        } else {
                            this.values.add(value);
                        }

                    }
                }
            }
        }
    }

    public Object[] getArgs() {
        return this.values.toArray();
    }

    public String getCondition() {
        return condition;
    }

    private String getPartHql() {
        String s = "";
        if (alias != null) {
            if ("LT".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(alias).append(".").append(property).append(" < ").append("?").toString();
            } else if ("GT".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(alias).append(".").append(property).append(" > ").append("?").toString();
            } else if ("LE".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(alias).append(".").append(property).append(" <= ").append("?").toString();
            } else if ("GE".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(alias).append(".").append(property).append(" >= ").append("?").toString();
            } else if ("LK".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(alias).append(".").append(property).append(" like ").append("?").toString();
            } else if ("NULL".equals(operation))
                s = (new StringBuilder()).append(" and ").append(alias).append(".").append(property).append(" is null ").toString();
            else if ("NOTNULL".equals(operation))
                s = (new StringBuilder()).append(" and ").append(alias).append(".").append(property).append(" is not null ").toString();
            else if ("NEQ".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(alias).append(".").append(property).append(" != ").append("?").toString();
            } else if ("EQ".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(alias).append(".").append(s).append(property).append(" = ").append("?").toString();
            }
        } else {
            if ("LT".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(property).append(" < ").append("?").toString();
            } else if ("GT".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(property).append(" > ").append("?").toString();
            } else if ("LE".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(property).append(" <= ").append("?").toString();
            } else if ("GE".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(property).append(" >= ").append("?").toString();
            } else if ("LK".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(property).append(" like ").append("?").toString();
            } else if ("NULL".equals(operation))
                s = (new StringBuilder()).append(" and ").append(property).append(" is null ").toString();
            else if ("NOTNULL".equals(operation))
                s = (new StringBuilder()).append(" and ").append(property).append(" is not null ").toString();
            else if ("NEQ".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(property).append(" != ").append("?").toString();
            } else if ("EQ".equals(operation)) {
                s = (new StringBuilder()).append(" and ").append(s).append(property).append(" = ").append("?").toString();
            }
        }
        return s;
    }

}