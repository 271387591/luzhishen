package com.ozstrategy.dao.standingbook;

import com.ozstrategy.dao.BaseDao;
import com.ozstrategy.model.standingbook.StandingBook;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/1/15.
 */
public interface StandingBookDao extends BaseDao<StandingBook>{
    List<Map<String,Object>> listBooks(Map<String,Object> params,Integer start,Integer limit) throws Exception;
    Integer listBooksCount(Map<String,Object> params) throws Exception;
}
