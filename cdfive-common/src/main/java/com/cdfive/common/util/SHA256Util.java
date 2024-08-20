package com.cdfive.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
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
}
