package ru.fmeter.edu.service;

import java.util.HashMap;
import java.util.Map;

public class TokenStore {
    private final static Map<String, String> tokens = new HashMap<>();

    static boolean add(String token, String login) {
        if (!tokens.containsValue(login)) {
            tokens.put(token, login);
            return true;
        }
        return false;
    }

    static String findUserByToken(String token) {
        return tokens.get(token);
    }

    static boolean delete(String token) {
        if (tokens.containsKey(token)) {
            tokens.remove(token);
            return true;
        }
        return false;
    }
}
