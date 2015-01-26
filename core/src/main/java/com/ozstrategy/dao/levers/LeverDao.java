package com.ozstrategy.dao.levers;

import com.ozstrategy.dao.GenericDao;
import com.ozstrategy.model.levers.Lever;

import java.util.List;

/**
 * Created by lihao on 1/23/15.
 */
public interface LeverDao extends GenericDao<Lever,Long> {
    List<Lever> list(String keyword,Integer start,Integer limit);
    Integer listCount(String keyword);
}
