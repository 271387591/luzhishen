package com.ozstrategy.service.standingbook;

import com.ozstrategy.model.standingbook.StandingBook;

import java.util.List;
import java.util.Map;

/**
 * Created by lihao1 on 5/1/15.
 */
public interface StandingBookManager {
    static final String MENU_KEY_PRE="Template_";
    static final String MENU_Parent_KEY="Template";
    static final String MENU_Temp_Widget="tableTemple";
    List<Map<String,Object>> list(Map<String,Object> params,Integer start,Integer limit) throws Exception;
    Integer listCount(Map<String,Object> params) throws Exception;
    StandingBook getBySerialNum(String serialNum);
    void save(StandingBook book);
    StandingBook getById(Long id);
    void delete(StandingBook book);
    StandingBook getByTableId(Long tableId);
}
