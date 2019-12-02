package com.jasonliu.alpha.data.crawler.tiger;

import com.jasonliu.alpha.data.IStockInfoService;
import com.jasonliu.alpha.bo.KlinePoint;
import com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlineItem;
import com.tigerbrokers.stock.openapi.client.https.request.quote.QuoteKlineRequest;
import com.tigerbrokers.stock.openapi.client.https.response.quote.QuoteKlineResponse;
import com.tigerbrokers.stock.openapi.client.struct.enums.KType;
import com.tigerbrokers.stock.openapi.client.struct.enums.RightOption;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Created by liujg on 2019-12-02
 */
@Service
public class TigerStockInfoServiceImpl implements IStockInfoService {

    @Override
    public Map<String, List<KlinePoint>> fetchHistoryPoint(List<String> symbols, String beginTime, String endTime) {
        QuoteKlineResponse
                response = TigerClient.getTigerHttpClient().execute(
                QuoteKlineRequest.newRequest(symbols, KType.day, beginTime, endTime)
                        .withLimit(1000)
                        .withRight(RightOption.br));
        if (response.isSuccess()) {
            return response.getKlineItems()
                    .stream()
                    .collect(Collectors.toMap(KlineItem::getSymbol, item -> convertPoints(item.getItems())));
        } else {
            return Collections.EMPTY_MAP;
        }
    }

    private List<KlinePoint> convertPoints(
            List<com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlinePoint> klinePoints) {
        return klinePoints.stream().map(this::convertPoint).collect(Collectors.toList());
    }

    private KlinePoint convertPoint(
            com.tigerbrokers.stock.openapi.client.https.domain.quote.item.KlinePoint klinePoint) {
        KlinePoint point = new KlinePoint();
        point.setOpen(klinePoint.getOpen());
        point.setClose(klinePoint.getClose());
        point.setHigh(klinePoint.getHigh());
        point.setLow(klinePoint.getLow());
        point.setTime(klinePoint.getTime());
        return point;
    }
}
