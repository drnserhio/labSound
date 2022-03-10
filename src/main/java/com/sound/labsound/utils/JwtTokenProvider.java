package com.sound.labsound.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sound.labsound.model.principal.CustomUserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(CustomUserPrincipal customUserPrincipal) {
        String[] claims = getClaimsForUser(customUserPrincipal);

        String token = JWT.create()
                .withIssuedAt(new Date()).withSubject(customUserPrincipal.getUsername())
                .withArrayClaim("authorities", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 432_000_000))
                .sign(Algorithm.HMAC512(secret.getBytes()));
        return token;
    }

    private String[] getClaimsForUser(CustomUserPrincipal customUserPrincipal) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority authority : customUserPrincipal.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

    public boolean isTokenValid(String username, String token) {
        JWTVerifier jwtVerifier = getJwtVerifier();
        return StringUtils.isNotEmpty(username) && !isTokenExpired(jwtVerifier, token);
    }

    private boolean isTokenExpired(JWTVerifier jwtVerifier, String token) {
        Date expires = jwtVerifier.verify(token).getExpiresAt();
        return expires.before(new Date());
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[]claims = getClaimsForToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public String getSubject(String token) {
        JWTVerifier jwtVerifier = getJwtVerifier();
        String subject = jwtVerifier.verify(token).getSubject();
        return subject;
    }

    private String[] getClaimsForToken(String token) {
        JWTVerifier jwtVerifier = getJwtVerifier();
        String[] subject = jwtVerifier.verify(token).getClaim("authorities").asArray(String.class);
        return subject;
    }

    private JWTVerifier getJwtVerifier() {
        JWTVerifier jwtVerifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            jwtVerifier = JWT.require(algorithm).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token didn't verifier");
        }
        return jwtVerifier;
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }
}
