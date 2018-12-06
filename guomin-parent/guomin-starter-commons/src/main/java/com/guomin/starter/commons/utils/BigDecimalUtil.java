package com.guomin.starter.commons.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {

    public static String getRsaValue(BigDecimal data){
        String plainString = data.stripTrailingZeros().toPlainString();
        String result = CryptoUtil.rsaEncrypt(plainString);
        return result;
    }

    public static BigDecimal rsaValueToBigDecimal(String data){
        String rsadecrypt = CryptoUtil.rsadecrypt(data);
        BigDecimal result = new BigDecimal(rsadecrypt);
        return result;
    }

}