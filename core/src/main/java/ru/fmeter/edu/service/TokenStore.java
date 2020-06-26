package ru.fmeter.edu.service;

import java.util.HashMap;
import java.util.Map;

public class TokenStore {
    private static Map<String, String> tokens = new HashMap<>();

    static void add(String token, String login) {
        if (tokens.size() >= 100) {
            tokens = new HashMap<>();
        }
        tokens.put(token, login);
    }

    static String findUserByToken(String token) {
        return tokens.get(token);
    }

    static String findTokenByUser(String login) {
        for (Map.Entry<String, String> entry : tokens.entrySet()) {
            if (entry.getValue().equals(login)) {
                return entry.getKey();
            }
        }
        return null;
    }

    static void delete(String token) { tokens.remove(token); }
}
