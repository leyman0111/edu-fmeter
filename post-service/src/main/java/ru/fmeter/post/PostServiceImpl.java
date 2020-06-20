package ru.fmeter.post;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class PostServiceImpl implements PostService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    public String addresser;

    private final static String ACTIVATION_SUBJECT = "Активация аккаунта";
    private final static String RECOVERY_SUBJECT = "Восстановление пароля";
    private final static String CERTIFICATE_SUBJECT = "Сертификат об успешном прохождении курса";

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
        sendTextMail(addressee, ACTIVATION_SUBJECT, message);
    }

    @Override
    public void sendRecoveryMail(String addressee, String secretKey) {
        String message = String.format("Запущена процедура восстановления пароля доступа к образовательному порталу " +
                "EDU.FMETER.RU. Для завершения просим Вас пройти по ссылке: " +
                "https://www.edu.fmeter.ru/recovery/%s . Если процедура инициирована не Вами, " +
                "просим проигнорировать данное сообщение или сообщить по электронному адресу: " +
                "info@fmeter.ru", secretKey);
        sendTextMail(addressee, RECOVERY_SUBJECT, message);
    }

    private void sendTextMail(String addressee, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(addresser);
        mailMessage.setTo(addressee);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendCertificate(String addressee, File file) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(addresser);
            messageHelper.setTo(addressee);
            messageHelper.setSubject(CERTIFICATE_SUBJECT);
            messageHelper.setText("Поздравляем с успешным прохождением курса!");
            messageHelper.addAttachment("Certificate.txt", file);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
