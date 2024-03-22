package com.cdfive.common.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * @author cdfive
 */
@Slf4j
public class MD5Util {
    public static String encodeByMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return byte2string(md.digest());
        } catch (Exception e) {
            log.error("encodeByMD5 error,str={}", str, e);
            return str;
        }
    }

    public static String encodeByMD5(String str, String salt) {
        return encodeByMD5(String.format("%s%s", str, salt));
    }

    public static String byte2string(byte[] b) {
        StringBuffer hs = new StringBuffer(100);
        for (int n = 0; n < b.length; n++) {
            hs.append(byte2fex(b[n]));
        }
        return hs.toString();
    }

    public static String byte2fex(byte ib) {
        char[] Digit =
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }
}
