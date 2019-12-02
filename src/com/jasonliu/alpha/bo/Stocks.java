package com.jasonliu.alpha.bo;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by liujg on 2019-12-02
 */
public class Stocks {

    //已买股票
    private static final List<Stock> cnOwns = Arrays.asList(new Stock("", ""), new Stock("", ""));
    private static final List<Stock> hkOwns = Arrays.asList(new Stock("00700", ""), new Stock("", ""));
    private static final List<Stock> usOwns = Arrays.asList(new Stock("", ""), new Stock("", ""));
    public static final List<Stock> owns = Lists.newArrayList();

    static {
        owns.addAll(cnOwns);
        owns.addAll(hkOwns);
        owns.addAll(usOwns);
    }

    //好公司股票--任意时间都能长期持久
    private static final List<Stock> cnQualitys = Arrays.asList(new Stock("00700", ""), new Stock("000651", ""));
    private static final List<Stock> hkQualitys = Arrays.asList(new Stock("00700", ""), new Stock("000651", ""));
    private static final List<Stock> usQualitys = Arrays.asList(new Stock("00700", ""), new Stock("000651", ""));
    public static final List<Stock> qualitys = Lists.newArrayList();

    static {
        qualitys.addAll(cnQualitys);
        qualitys.addAll(hkQualitys);
        qualitys.addAll(usQualitys);
    }

    //待观察公司股票
    private static final List<Stock> cnObservers = Arrays.asList(new Stock("00700", ""), new Stock("000651", ""));
    private static final List<Stock> hkObservers = Arrays.asList(new Stock("00700", ""), new Stock("000651", ""));
    private static final List<Stock> usObservers = Arrays.asList(new Stock("00700", ""), new Stock("000651", ""));
    public static final List<Stock> observers = Lists.newArrayList();

    static {
        observers.addAll(cnObservers);
        observers.addAll(hkObservers);
        observers.addAll(usObservers);
    }

    public static List<String> convert(List<Stock> stocks) {
        return stocks.stream().map(Stock::getSymbol).filter(s -> s.length() > 0).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class Stock {

        private String symbol;
        private String name;
    }
}
