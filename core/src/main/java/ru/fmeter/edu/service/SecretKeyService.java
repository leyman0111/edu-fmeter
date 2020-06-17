package ru.fmeter.edu.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecretKeyService {
    public String generate(String login) {
        String secretKey = UUID.randomUUID().toString();
        return SecretKeyStore.add(secretKey, login) ? secretKey : null;
    }

    public String validate(String secretKey) {
        return SecretKeyStore.findUserBySecretKey(secretKey);
    }

    public void delete(String secretKey) {
        SecretKeyStore.delete(secretKey);
    }
}
