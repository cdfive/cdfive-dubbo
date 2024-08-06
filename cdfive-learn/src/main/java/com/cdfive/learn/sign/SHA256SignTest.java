package com.cdfive.learn.sign;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * @author cdfive
 */
public class SHA256SignTest {

    public static void main(String[] args) throws Exception {
        String secret = "abc";
        Long timestamp = System.currentTimeMillis();
        String sign = getSign(secret, timestamp);
        System.out.println(sign);
    }

    public static String getSign(String secret, Long timestamp) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return sign;
    }
}
