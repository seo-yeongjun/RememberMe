package com.zanygeek.rememberme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//회원 가입시 메일 발송을 위해 만들었으나
//비밀번호 찾기 등 따로 쓰일 수 있어 빼둠
@Service
public class MailSenderService {
    private JavaMailSender javaMailSender;

    @Autowired
    public MailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Async//비동기
    public void sendMail(SimpleMailMessage mail) {
        javaMailSender.send(mail);
    }
}
