package com.jasonliu.alpha.data;

import com.jasonliu.alpha.bo.KlinePoint;
import java.util.List;
import java.util.Map;

/**
 * Created by liujg on 2019-12-02
 */
public interface IStockInfoService {

    Map<String, List<KlinePoint>> fetchHistoryPoint(List<String> symbols, String beginTime, String endTime);
}
