package com.cdfive.framework.component.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.JwtMap;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @author cdfive
 */
@Slf4j
public class JwtComponent {

    private String jwtSecret;

    private Long accessTokenExpire = 1000 * 60 * 30L;

    private SecretKeySpec key;

    public JwtComponent(String jwtSecret, Long accessTokenExpire) {
        this.jwtSecret = jwtSecret;
        this.accessTokenExpire = accessTokenExpire;
        this.key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }

    public String createToken(JwtClaims claims) {
        claims.setExpireTime(System.currentTimeMillis() + accessTokenExpire);
        String compactJws = Jwts.builder().setPayload(JSONObject.toJSONString(claims))
                .compressWith(CompressionCodecs.DEFLATE).signWith(SignatureAlgorithm.HS512, key).compact();
        return compactJws;
    }

    public JwtClaims parseToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            String jsonData = JSON.toJSONString(body);
            JwtClaims userClaims = JSON.parseObject(jsonData, JwtClaims.class);
            return userClaims;
        } catch (Exception e) {
            log.error("parseToken error", e);
        }

        return null;
    }

    public static class JwtClaims extends JwtMap implements Claims {

        private Long expireTime;

        private Long userId;

        private String userName;

        public Long getExpireTime() {
            Object object = this.get("expireTime");
            if (object == null) {
                return null;
            }
            this.expireTime = Long.parseLong(object.toString());
            return expireTime;
        }


        public Long getUserId() {
            Object object = this.get("userId");
            if (object == null) {
                return null;
            }
            this.userId = Long.parseLong(object.toString());
            return userId;
        }

        public String getUserName() {
            Object object = this.get("userName");
            if (object == null) {
                return null;
            }
            this.userName = object.toString();
            return userName;
        }

        @Override
        public String getIssuer() {
            return getString(ISSUER);
        }

        @Override
        public Claims setIssuer(String iss) {
            setValue(ISSUER, iss);
            return this;
        }

        @Override
        public String getSubject() {
            return getString(SUBJECT);
        }

        @Override
        public Claims setSubject(String sub) {
            setValue(SUBJECT, sub);
            return this;
        }

        @Override
        public String getAudience() {
            return getString(AUDIENCE);
        }

        @Override
        public Claims setAudience(String aud) {
            setValue(AUDIENCE, aud);
            return this;
        }

        @Override
        public Date getExpiration() {
            return get(Claims.EXPIRATION, Date.class);
        }

        @Override
        public Claims setExpiration(Date exp) {
            setDate(Claims.EXPIRATION, exp);
            return this;
        }

        @Override
        public Date getNotBefore() {
            return get(Claims.NOT_BEFORE, Date.class);
        }

        @Override
        public Claims setNotBefore(Date nbf) {
            setDate(Claims.NOT_BEFORE, nbf);
            return this;
        }

        @Override
        public Date getIssuedAt() {
            return get(Claims.ISSUED_AT, Date.class);
        }

        @Override
        public Claims setIssuedAt(Date iat) {
            setDate(Claims.ISSUED_AT, iat);
            return this;
        }

        @Override
        public String getId() {
            return getString(ID);
        }

        @Override
        public Claims setId(String jti) {
            setValue(Claims.ID, jti);
            return this;
        }

        @Override
        public <T> T get(String claimName, Class<T> requiredType) {
            Object value = get(claimName);
            if (value == null) {
                return null;
            }

            if (Claims.EXPIRATION.equals(claimName)
                    || Claims.ISSUED_AT.equals(claimName)
                    || Claims.NOT_BEFORE.equals(claimName)) {
                value = getDate(claimName);
            }

            if (requiredType == Date.class && value instanceof Long) {
                value = new Date((Long) value);
            }

            if (!requiredType.isInstance(value)) {
                throw new RequiredTypeException("Expected value to be of type: "
                        + requiredType + ", but was " + value.getClass());
            }

            return requiredType.cast(value);
        }

        protected void setExpireTime(Long expireTime) {
            this.expireTime = expireTime;
            setValue("expireTime", this.expireTime);
        }

        public void setUserId(Long userId) {
            this.userId = userId;
            setValue("userId", this.userId);
        }

        public void setUserName(String userName) {
            this.userName = userName;
            setValue("userName", this.userName);
        }
    }
}
