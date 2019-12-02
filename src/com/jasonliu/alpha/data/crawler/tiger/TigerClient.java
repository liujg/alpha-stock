package com.jasonliu.alpha.data.crawler.tiger;

import com.tigerbrokers.stock.openapi.client.https.client.TigerHttpClient;

/**
 * Created by liujg on 2019-12-02
 */
public class TigerClient {

    private static TigerHttpClient httpClient = getHttpInstance();

    private static TigerHttpClient getHttpInstance() {
        String serverUrl = "https://openapi.itiger.com/gateway";
        String tigerId = "20150524";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL74sCL0cCtJjee9"
                + "VL4ZGwLeBFasl5q8oEu/2IOE5mS43ZHhchNPsiRz8EajcsQTKEjx6oW9ZbDq987W"
                + "Gw0kUl+XqdbHBntFtRkcFsTiUhkeWOO6m+ScyTlNQ4S1mmI2/m7H6oHGqzuLvrfT"
                + "gztKDKHy/YQe9pm2GED+VNjd3c/ZAgMBAAECgYEAlJXL/bZwW83EAm22NJ41/Pof"
                + "KAzrfDKNCcL/w/ywgeewRWPCrTHR6jjm1x8vGU3Kh07qaRpet9Np8d12eLgs+ell"
                + "jDjYHpkLnBh+fGbcoMHxR3LgBDn96V7P0KA7esoOi6pEdYiU4iUgCUm85FiMuJrt"
                + "otypTWqa2mzmRUg3ozECQQDdrQYvL0dptuEx/iLyUeOw5T0p1Nu0w+qaUg3E6ScE"
                + "22cQG6IEzV+utzGGy31cOGncydT7vwWxCUkSnKYY2cA9AkEA3IqTTQxynVyAqS7v"
                + "Ism5IyiG66vSvXyFseNTH68AhMXBe3N7YysBT6vkvH48m+JXSSx7FB1Ygd3ZB+qI"
                + "arFLzQJBAMEELyZWnG/H9T16cyNbek5EzITn+oXjMRs95w9Vk8v+3jaAWQqlaHLQ"
                + "wCcGeBkcZdEBTJoHMgV8eEvkahupPmECQBPAATL8M2NuPkNoZZf68q54WaTPTV8U"
                + "wLBOiEolFOnO/+W7UMhvuH1XCnPq5iibm299IUSK4Ds5D8+KrE7Js90CQE1YMOhM"
                + "SVOvBv88BDfWUZT246OVou3AbKoXu8BbF1moJgzmuDtCuHXvwFh5ZUTvkl4p0Xln"
                + "aat8LahfC0uUU7s=";
        return new TigerHttpClient(serverUrl, tigerId, privateKey);
    }

    public static TigerHttpClient getTigerHttpClient() {
        return httpClient;
    }
}
