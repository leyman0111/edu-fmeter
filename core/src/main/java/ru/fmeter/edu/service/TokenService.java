package ru.fmeter.edu.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;

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

    public String generate(User user) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("userId", user.getId());
        tokenData.put("login", user.getLogin());
        tokenData.put("created", new Date());
        Calendar expDate = Calendar.getInstance();
        expDate.add(Calendar.DATE, 1);
        tokenData.put("expirationDate", expDate);
        Key key = new SecretKeySpec(
                DatatypeConverter.parseBase64Binary(TOKEN_DECRYPTION_KEY),
                SignatureAlgorithm.HS512.getJcaName());
        String token = Jwts.builder()
                .setExpiration(expDate.getTime())
                .setClaims(tokenData)
                .signWith(key)
                .compact();
        return TokenStore.add(token, user.getLogin()) ? token : null;
    }

    public String validate(String token) {
        return isValid(token) ? TokenStore.findUserByToken(token) : null;
    }

    public boolean reject(String token) {
        return TokenStore.delete(token);
    }

    private boolean isValid(String token) {
        DefaultClaims claims = (DefaultClaims) Jwts.parserBuilder().setSigningKey(TOKEN_DECRYPTION_KEY).build()
                .parse(token).getBody();
        Long expDate = claims.get("expirationDate", Long.class);
        if (expDate != null && new Date(expDate).after(new Date())) {
            return true;
        } else {
            reject(token);
            return false;
        }
    }
}
