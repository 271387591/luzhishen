package com.ozstrategy.service.levers;

import com.ozstrategy.model.levers.Lever;
import com.ozstrategy.service.GenericManager;

import java.util.List;

/**
 * Created by lihao on 1/23/15.
 */
public interface LeverManager extends GenericManager<Lever,Long> {
    List<Lever> list(String keyword,Integer start,Integer limit);
    Integer listCount(String keyword);
}
