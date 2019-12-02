package com.jasonliu.alpha.strategy;

import com.google.common.collect.Lists;
import com.jasonliu.alpha.bo.Stocks;
import com.jasonliu.alpha.data.IStockInfoService;
import com.jasonliu.alpha.data.crawler.tiger.TigerStockInfoServiceImpl;
import com.jasonliu.alpha.bo.KlinePoint;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * 压力线、支持线检测
 * Created by liujg on 2019-11-23
 */
public class HighLowDetect {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final int CONTINUE_DAY = 10;

    //前后CONTINUE_DAY天都大于或小于当天，则为谷底或峰值
    public static List<DetectPoint> detect(List<DetectPoint> klinePoints) {
        List<DetectPoint> inflections = Lists.newArrayList();
        for (int i = 0; i < klinePoints.size(); i++) {
            int start = i - CONTINUE_DAY > 0 ? i - CONTINUE_DAY : 0;
            int end = i + CONTINUE_DAY < klinePoints.size() ? i + CONTINUE_DAY : klinePoints.size();
            Double close = klinePoints.get(i).getClose();
            boolean high = true;
            boolean low = true;
            for (int j = start; j < end; j++) {
                if (j == i) {
                    continue;
                }
                Double tmp = klinePoints.get(j).getClose();
                if (close > tmp) {
                    low = false;
                } else {
                    high = false;
                }
            }

            if (high) {
                klinePoints.get(i).setInflection(1);
                inflections.add(klinePoints.get(i));
            } else if (low) {
                klinePoints.get(i).setInflection(2);
                inflections.add(klinePoints.get(i));
            }
        }
        return inflections;
    }

    private static void cluster(List<DetectPoint> inflections) {
        boolean cluster = true;
        while (cluster) {
            cluster = false;
            //连续状态合并
            for (int i = 1; i < inflections.size(); ) {
                int tmp1 = inflections.get(i - 1).getInflection();
                int tmp2 = inflections.get(i).getInflection();
                if (tmp1 == tmp2) {
                    if (tmp1 == 1) {
                        if (inflections.get(i - 1).getClose() > inflections.get(i).getClose()) {
                            inflections.remove(i);
                        } else {
                            inflections.remove(i - 1);
                        }
                    } else {
                        if (inflections.get(i - 1).getClose() > inflections.get(i).getClose()) {
                            inflections.remove(i - 1);
                        } else {
                            inflections.remove(i);
                        }
                    }
                } else {
                    i++;
                }
            }

            //波动小于8%的剔除掉
            for (int i = 1; i < inflections.size(); ) {
                double rate = (inflections.get(i).close - inflections.get(i - 1).close) / inflections.get(i - 1).close;
                if (Math.abs(rate) < 0.08) {
                    inflections.remove(i);
                    cluster = true;
                } else {
                    i++;
                }
            }
        }
    }

    private static void output(List<DetectPoint> inflections) {
        for (int i = 0; i < inflections.size(); i++) {
            double rate = 0;
            if (i > 0) {
                rate = (inflections.get(i).close - inflections.get(i - 1).close) / inflections.get(i - 1).close;
            }

            System.out.println(
                    FORMATTER.format(inflections.get(i).time) + "=" + inflections.get(i).getClose() + "=" + rate);
        }
    }

    public static void main(String[] args) {
        IStockInfoService stockInfoService = new TigerStockInfoServiceImpl();
        Map<String, List<KlinePoint>> pointMap =
                stockInfoService.fetchHistoryPoint(Stocks.convert(Stocks.owns), "2010-07-23", "2019-11-22");
        for (Map.Entry<String, List<KlinePoint>> entry : pointMap.entrySet()) {
            String symbol = entry.getKey();
            System.out.println("---------------------" + symbol + "------------------------");
            List<KlinePoint> klinePoints = entry.getValue();
            //检测
            List<DetectPoint> inflections =
                    detect(klinePoints.stream().map(HighLowDetect::convert).collect(Collectors.toList()));

            //聚类
            cluster(inflections);

            //输出结果
            output(inflections);
        }
    }

    public static DetectPoint convert(KlinePoint klinePoint) {
        DetectPoint detectPoint = new DetectPoint();
        detectPoint.setClose(klinePoint.getClose());
        detectPoint.setTime(klinePoint.getTime());
        return detectPoint;
    }

    @Data
    public static class DetectPoint {

        private Double close;
        private Long time;
        private int inflection;//0为默认，1为峰值，2为谷底
    }
}
