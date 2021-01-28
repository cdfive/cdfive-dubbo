package com.cdfive.admin.security;

import com.cdfive.common.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author cdfive
 */
@Component
public class Md5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.encodeByMD5((String) rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        return false;
        return Objects.equals(encodedPassword, MD5Util.encodeByMD5((String) rawPassword));
    }
}
