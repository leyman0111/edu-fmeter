package ru.fmeter.edu.service;

import java.util.HashMap;
import java.util.Map;

public class SecretKeyStore {
    private static Map<String, String> secretKeys = new HashMap<>();

    static boolean add(String secretKey, String login) {
        if (secretKeys.size() >= 100) {
            secretKeys = new HashMap<>();
        }
        if (!secretKeys.containsValue(login)) {
            secretKeys.put(secretKey, login);
            return true;
        }
        return false;
    }

    static String findUserBySecretKey(String secretKey) {
        return secretKeys.get(secretKey);
    }

    static void delete(String secretKey) {
        secretKeys.remove(secretKey);
    }
}
