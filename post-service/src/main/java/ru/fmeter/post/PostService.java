package ru.fmeter.post;

import java.io.File;

public interface PostService {
    void sendActivationMail(String addressee, String secretKey);

    void sendRecoveryMail(String addressee, String secretKey);

    void sendCertificate(String addressee, File file);

    void sendTestMail(String addressee, String userSecretKey, String testSecretKey);
}
