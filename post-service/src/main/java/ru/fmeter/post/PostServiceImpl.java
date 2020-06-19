package ru.fmeter.post;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PostServiceImpl implements PostService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String addresser;

    private final static String ACTIVATION_SUBJECT = "Активация аккаунта";
    private final static String RECOVERY_SUBJECT = "Восстановление пароля";
    private final static String CERTIFICATE_SUBJECT = "Сертификат об успешном прохождении курса ";

    public PostServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendActivationMail(String addressee, String secretKey) {
        String message = String.format("Вы зарегистрированы на образовательном портале EDU.FMETER.RU. " +
                "Для завершения процедуры регистрации просим Вас пройти по ссылке: " +
                "https://www.edu.fmeter.ru/activation/%s . Если процедура инициирована не Вами, " +
                "просим проигнорировать данное сообщение или сообщить по электронному адресу: " +
                "info@fmeter.ru", secretKey);
        sendMail(addressee, ACTIVATION_SUBJECT, message);
    }

    @Override
    public void sendRecoveryMail(String addressee, String secretKey) {
        String message = String.format("Запущена процедура восстановления пароля доступа к образовательному порталу " +
                "EDU.FMETER.RU. Для завершения просим Вас пройти по ссылке: " +
                "https://www.edu.fmeter.ru/recovery/%s . Если процедура инициирована не Вами, " +
                "просим проигнорировать данное сообщение или сообщить по электронному адресу: " +
                "info@fmeter.ru", secretKey);
        sendMail(addressee, RECOVERY_SUBJECT, message);
    }

    @Override
    public void sendCertificate(String addressee, File file) { }

    private boolean sendMail(String addressee, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(addresser);
        mailMessage.setTo(addressee);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
        return true;
    }
}
