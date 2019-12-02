package com.jasonliu.alpha.bo;

import lombok.Data;

/**
 * Created by liujg on 2019-12-02
 */
@Data
public class KlinePoint {

    private Double open;

    private Double close;

    private Double high;

    private Double low;

    private Long time;
}
