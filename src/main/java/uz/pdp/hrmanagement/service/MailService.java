package uz.pdp.hrmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String content){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(subject);
            mailMessage.setFrom("HrManagement");
            mailMessage.setTo(to);
            mailMessage.setText(content);
            javaMailSender.send(mailMessage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendVerify(String to, String emailCode){
        String content="<a href = 'http://localhost:8081/api/user/savePassword?emailCode=" + emailCode + "&email=" + to + "'>parol kiritish uchun</a>";

        String subject = "ro'xatdan o'tdingiz, parol o'rnating";

        sendEmail(to, subject, content);
    }

}
