package com.softech.ehr.security;

import com.softech.ehr.model.security.SecurityUser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    private final String AUDIENCE_UNKNOWN = "unknown";
    private final String AUDIENCE_WEB = "web";
    private final String AUDIENCE_MOBILE = "mobile";
    private final String AUDIENCE_TABLET = "tablet";

    @Value("${az.access.token.secret}")
    private String secret;

    @Value("${az.access.token.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Instant getExpirationDateFromToken(String token) {
        Instant expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration().toInstant();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            audience = (String) claims.get("audience");
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Instant generateExpirationDate() {
        return Instant.now().plusMillis(this.expiration);
    }

    private Boolean isTokenExpired(String token) {
        final Instant expiration = this.getExpirationDateFromToken(token);
        return expiration.compareTo(Instant.now()) < 0;
    }

    private String generateAudience(String device) {
        String audience = this.AUDIENCE_UNKNOWN;
        if (device.equalsIgnoreCase(this.AUDIENCE_WEB)) {
            audience = this.AUDIENCE_WEB;
        } else if (device.equalsIgnoreCase(this.AUDIENCE_TABLET)) {
            audience = AUDIENCE_TABLET;
        } else if (device.equalsIgnoreCase(this.AUDIENCE_MOBILE)) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = this.getAudienceFromToken(token);
        return (this.AUDIENCE_TABLET.equals(audience) ||
            this.AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserDetails userDetails, String device) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("sub", userDetails.getUsername());
        claims.put("audience", this.generateAudience(device));
        claims.put("created", this.generateCurrentDate());
        return this.generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date.from(this.generateExpirationDate()))
            .signWith(SignatureAlgorithm.HS512, this.secret)
            .compact();
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        SecurityUser user = (SecurityUser) userDetails;
        final String username = this.getUsernameFromToken(token);
        return (username.equals(user.getUsername()) &&
            !(this.isTokenExpired(token)));
    }

}
