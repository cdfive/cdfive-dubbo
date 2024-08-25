package com.cdfive.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author cdfive
 */
@Slf4j
public class SHA256Util {

    public static String encodeBySHA256(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            return Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            log.error("encodeBySHA256 NoSuchAlgorithmException error,str={}", str, e);
            throw new RuntimeException("encodeBySHA256 error", e);
        } catch (UnsupportedEncodingException e) {
            log.error("encodeBySHA256 UnsupportedEncodingException error,str={}", str, e);
            throw new RuntimeException("encodeBySHA256 error", e);
        } catch (Exception e) {
            log.error("encodeBySHA256 error,str={}", str, e);
            throw new RuntimeException("encodeBySHA256 error", e);
        }
    }

    public static String signBySHA256(String secret, Long timestamp) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return sign;
    }
}
