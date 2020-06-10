package ru.fmeter.edu.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fmeter.dao.model.User;
import ru.fmeter.dao.service.UserService;
import ru.fmeter.dto.LoginDto;
import ru.fmeter.dto.UserDto;
import ru.fmeter.edu.mapper.UserMapper;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    private static final String TOKEN_DECRYPTION_KEY = "CmEyuNkONsSFyD8pryDdJisVvWAOEAE1jtVDg8e4GbhCk";

    private final UserService userService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginService(UserService userService, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Boolean> register(UserDto userDTO) {
        return new ResponseEntity<>(
                userService.create(userMapper.userDtoToUser(userDTO)),
                HttpStatus.OK);

    }

    public ResponseEntity<Boolean> activate(Long id, String key) {
        throw new UnsupportedOperationException("Operation temporarily unsupported");
    }

    public ResponseEntity<String> login(LoginDto login) {
        User user = (User) userService.loadUserByUsername(login.getUsername());
        if (user.getPass().equals(login.getPassword())) {
            return new ResponseEntity<>(
                    generateToken(user),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("INCORRECT PASSWORD", HttpStatus.OK);
    }

    public ResponseEntity<Boolean> logout() {
        return null;
    }

    public void recover(String login) { }

    private String generateToken(User user) {
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
        return Jwts.builder()
                .setExpiration(expDate.getTime())
                .setClaims(tokenData)
                .signWith(key)
                .compact();
    }
}
