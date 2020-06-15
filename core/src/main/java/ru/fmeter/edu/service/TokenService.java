package ru.fmeter.edu.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    private static final String TOKEN_DECRYPTION_KEY = "CmEyuNkONsSFyD8pryDdJisVvWAOEAE1jtVDg8e4GbhCk";

    public String generate(Long userId, String login) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("userId", userId);
        tokenData.put("login", login);
        tokenData.put("created", new Date());
        Calendar expDate = Calendar.getInstance();
        expDate.add(Calendar.DATE, 1);
        tokenData.put("expirationDate", expDate);
        Key key = new SecretKeySpec(
                DatatypeConverter.parseBase64Binary(TOKEN_DECRYPTION_KEY),
                SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder()
                .setExpiration(expDate.getTime())
                .setClaims(tokenData)
                .signWith(key)
                .compact();
    }

    public String parseLogin(String token) throws AuthenticationServiceException {
        DefaultClaims claims;
        try {
            claims = (DefaultClaims) Jwts.parserBuilder().setSigningKey(TOKEN_DECRYPTION_KEY).build().parse(token).getBody();
        } catch (Exception e) {
            throw new AuthenticationServiceException("Token corrupted");
        }
        if (claims.get("expirationDate", Long.class) == null) {
            throw new AuthenticationServiceException("Invalid token");
        }
        Date expDate = new Date(claims.get("expirationDate", Long.class));
        if (expDate.after(new Date())) return claims.get("login", String.class);
        else throw new AuthenticationServiceException("Token expired");
    }
}
