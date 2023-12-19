package com.dostavka24.dostavka24.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private long tokenValidateMillisecondRemember;
    private long tokenValidateMilliseconds;
    private static final String authKey = "auth";
    private final Key key;
    private final JwtParser jwtParser;
    public TokenProvider(){
        byte[] keyByte;
        String secret = "Um9uYWxkbyBpcyBncmVhdGVzdCBwbGF5ZXIgb2YgYWxsIHRpbWUgLHJlYWwgR09BVA0";
        keyByte = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyByte);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidateMillisecondRemember = 1000 * 86400;
        this.tokenValidateMilliseconds = 1000 * 3600;
    }

    public String createToken(Authentication authentication, Boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validate;
        if(rememberMe){
            validate = new Date(now + tokenValidateMillisecondRemember);
        } else {
            validate = new Date(now + tokenValidateMilliseconds);
        }
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(authKey,authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validate)
                .compact();
    }

    public boolean validateToken(String jwt) {
        try {
            jwtParser.parseClaimsJws(jwt);
            return true;
        }catch (ExpiredJwtException expiredJwtException){
            logger.error("ExpiredJwtException");
        }catch (UnsupportedJwtException unsupportedJwtException){
            logger.error("UnsupportedJwtException");
        }catch (MalformedJwtException malformedJwtException){
            logger.error("MalformedJwtException");
        }catch (SignatureException signatureException){
            logger.error("SignatureException");
        }catch (IllegalArgumentException illegalArgumentException){
            logger.error("IllegalArgumentException");
        }
        return false;
    }

    //role ni olish
    public Authentication getAuthentication(String jwt) {
        Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(authKey).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "",authorities);
        return new UsernamePasswordAuthenticationToken(principal,jwt,authorities);
    }
}
