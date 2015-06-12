package com.ozstrategy.dao.order;

import com.ozstrategy.dao.GenericDao;
import com.ozstrategy.model.order.CreditsOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/28/15.
 */
public interface CreditsOrderDao extends GenericDao<CreditsOrder,Long> {
    List<Map<String,Object>> getOrderChart();
}
