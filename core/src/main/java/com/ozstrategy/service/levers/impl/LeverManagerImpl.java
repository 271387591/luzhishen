package com.ozstrategy.service.levers.impl;

import com.ozstrategy.dao.levers.LeverDao;
import com.ozstrategy.model.levers.Lever;
import com.ozstrategy.service.GenericManagerImpl;
import com.ozstrategy.service.levers.LeverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lihao on 1/23/15.
 */
@Service("leverManager")
public class LeverManagerImpl extends GenericManagerImpl<Lever,Long> implements LeverManager {
    @Autowired
    private LeverDao leverDao;

    public List<Lever> list(String keyword, Integer start, Integer limit) {
        return leverDao.list(keyword,start,limit);
    }

    public Integer listCount(String keyword) {
        return leverDao.listCount(keyword);
    }
}
