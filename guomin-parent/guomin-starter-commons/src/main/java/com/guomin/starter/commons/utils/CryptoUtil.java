package com.guomin.starter.commons.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class CryptoUtil {

    public static final String PRIVATE_KEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMdfAEF4jElKb6Gtx/LCL6zHdS46TkdpM/EcHdFzPCo7Wbp9O4eLMFyf1E1YE10zrl4hKUZbeN+8mFuijiaTHzozRYtBk9oUglb04IhHZMZ1C0xuaWpvZj6jolMSYs2SH29yYIBCOXudDxeczkgN8LGM2ApSQyDn5ifOnbNLeDhFAgMBAAECgYAB+jU8cP+jMnPM/LZA+CAU7J2C6d0g5DA5sfexvhzYpmILcXaHr5+5K3Sll7wJbpvKHzYTlpCqV2qDbidOxqLGooCgYJG65FO8KaKcSd2EkhG6p0e0Fg6jtlEGc2MUg+uMc5bnix1nv1RVgLMPkTEMbtLVi903OyaMOXgJThJvuQJBAPTAxFJRdVuyO2FO3BoP/ctqbsgjzFqvXJkF+wS1ob16Ac1ewZnOU2sp0CbCGxrJ2ow8lXDN8bSI2+wO+TxeBBcCQQDQiF59AHCDymanvpnfbQfXMk3kW+woHvL+gUKflZQzJONZpFnTXjbfJ+e+SxYB4BhuYpjW6ugp/86iptcpMrQDAkEA8iZmFiuRuQ81wKiNCxwXNVIwGMOSQ4aMTrmkCJzLUPONJOtDEDEaR0QvNKmGt5JOL8Eg+WJIaR2euIZ4AEnkMQJAeXj2kZpP8HVWlyBQQfYhOxnyhbOGPVjJ87cYOB3J1oEGbpFuQdH/5L46IH9csnr3wFVu8sKjutNoaxcE/tqqQQJBAO6/MzL49wmeS2EcgSVfKC6sgo/nPlIYX+DS0Y11X5tpVTtFPak6cJN6a2oWY3fZGVP5fOsPqbr1Vib0aSIlb/M=";
    public static final String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHXwBBeIxJSm+hrcfywi+sx3UuOk5HaTPxHB3RczwqO1m6fTuHizBcn9RNWBNdM65eISlGW3jfvJhboo4mkx86M0WLQZPaFIJW9OCIR2TGdQtMbmlqb2Y+o6JTEmLNkh9vcmCAQjl7nQ8XnM5IDfCxjNgKUkMg5+Ynzp2zS3g4RQIDAQAB";

    public static RSA rsa;

    public static String sha256(String data){
        return new Digester(DigestAlgorithm.SHA256).digestHex(data);
    }


    static {
        rsa = new RSA(PRIVATE_KEY,PUBLIC_KEY);
    }

    public static String rsaEncrypt(String data){
        String result = rsa.encryptBase64(data, CharsetUtil.CHARSET_UTF_8, KeyType.PublicKey);
        return result;
    }

    public static String rsadecrypt(String data){
        byte[] bytes = rsa.decryptFromBase64(data, KeyType.PrivateKey);
        String result = StrUtil.str(bytes, CharsetUtil.CHARSET_UTF_8);
        return result;
    }
}
