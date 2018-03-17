package springbook.user.service;

import java.util.Arrays;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {

    public void send(SimpleMailMessage mailMessage) throws MailException {
        System.out.println("DummayMailSender::send() is called.. " + mailMessage.toString());
    }

    public void send(SimpleMailMessage[] mailMessage) throws MailException {
        System.out.println("DummayMailSender::send([]) is called.." + Arrays.toString(mailMessage));
    }
}

