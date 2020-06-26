package ru.fmeter.edu.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SecretKeyStore {
    private static Map<String, String> secretKeys = new HashMap<>();

    static String generate(String login) {
        if (secretKeys.size() >= 100) {
            secretKeys = new HashMap<>();
        }
        String secretKey = UUID.randomUUID().toString();
        secretKeys.put(secretKey, login);
        return secretKey;
    }

    static Optional<String> findLogin(String secretKey) {
        return Optional.ofNullable(secretKeys.get(secretKey));
    }

    static void delete(String secretKey) {
        secretKeys.remove(secretKey);
    }
}
