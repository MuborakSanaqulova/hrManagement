package uz.pdp.hrmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String content){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            message.setSubject(subject);
            message.setFrom("test@gmail.com");
            message.setTo(to);
            message.setText(content);
            javaMailSender.send(mimeMessage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendVerify(String to, String emailCode){
        String content="<a href = 'http://localhost:8081/api/user/savePassword?emailCode=" + emailCode + "&email=" + to + "'>parol kiritish uchun</a>";

        String subject = "ro'xatdan o'tdingiz, parol o'rnating";

        sendEmail(to, subject, content);
    }

    public void sendVerifyTask(String to, String emailCode){

        String content="vazifa :)    "+emailCode+"&email="+to;

        String subject = "Sizga vazifa berildi";

        sendEmail(to, subject, content);

    }

    public void sendDoneTaskMessage(String to, String emailCode){

        String content="Vazifa yakunlandi    "+emailCode+"&email="+to;

        String subject = "vazifa tugatildi";

        sendEmail(to, subject, content);

    }

}
