package com.ozstrategy.service.aps;

import com.ozstrategy.model.aps.Aps;
import com.ozstrategy.service.GenericManager;

import java.util.List;

/**
 * Created by lihao1 on 5/25/15.
 */
public interface ApsManager extends GenericManager<Aps,Long> {
    List<Aps> getAp(Long user_id,String SSID,String BSSID);
}
