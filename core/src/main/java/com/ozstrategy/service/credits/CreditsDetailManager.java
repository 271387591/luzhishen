package com.ozstrategy.service.credits;

import com.ozstrategy.dao.GenericDao;
import com.ozstrategy.model.credits.CreditsDetail;
import com.ozstrategy.service.GenericManager;

import java.util.List;

/**
 * Created by lihao1 on 5/24/15.
 */
public interface CreditsDetailManager extends GenericManager<CreditsDetail,Long> {
    List<CreditsDetail> listDetails(String username,Long creditsId,Integer type,Integer start,Integer limit);
    Integer listDetailsCount(String username,Long creditsId,Integer type);
}
