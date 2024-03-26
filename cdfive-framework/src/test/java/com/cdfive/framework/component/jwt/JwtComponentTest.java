package com.cdfive.framework.component.jwt;

import org.junit.Test;

/**
 * @author cdfive
 */
public class JwtComponentTest {

    private static String jwtSecret = "testJwt";

    private static Long accessTokenExpire = 1000 * 60 * 60 * 12L;

    private static JwtComponent component = null;

    static {
        component = new JwtComponent(jwtSecret, accessTokenExpire);
    }

    @Test
    public void testCreateToken() {
        JwtComponent.JwtClaims claims = new JwtComponent.JwtClaims();
        claims.setUserId(100001L);
        String token = component.createToken(claims);
        System.out.println(token);
    }
}
