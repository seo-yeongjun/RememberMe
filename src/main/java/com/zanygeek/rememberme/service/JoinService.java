package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.MemberToken;
import com.zanygeek.rememberme.entity.NaverForm;
import com.zanygeek.rememberme.form.JoinForm;
import com.zanygeek.rememberme.repository.MemberRepository;
import com.zanygeek.rememberme.repository.MemberTokenRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.LinkedHashMap;


@Service
@Log4j2
public class JoinService {
    @Value("${rememberme.uri}")
    private String uri;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberTokenRepository memberTokenRepository;
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    PasswordEncoder passwordEncoder;

    //회원가입 폼을 통한 메일 전송 및 리파지토리 저장
    public void joinMember(JoinForm form) {
        //폼 맴버 전환
        Member member = formToMember(form);
        //비밀번호 해쉬암호화
        String encPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encPassword);
        member.setMemberFrom("rememberMe");
        //메일 인증 토큰 생성
        MemberToken token = new MemberToken(member.getUserId());
        //멤버, 토큰 저장
        try {
            memberRepository.save(member);
            memberTokenRepository.save(token);
        } catch (Exception e) {
            log.error(e);
        }
        //인증 메시지 발송
        sendConfirmMail(member, token);
    }

    //네이버 로그인 확인
    public Member joinNaverMember(NaverForm form) {
        if (form.getMessage().equals("success")) {
            Member member = new Member();
            if (!memberRepository.existsByUserId(form.getId())) {
                member.setUserId(form.getId());
                member.setMemberFrom("naver");
                member.setEnabled(true);
                member.setEmail(form.getEmail());
                member.setUserId(form.getId());
                member.setPhoneNumber(form.getMobile());
                member.setName(form.getName());
                memberRepository.save(member);
                return member;
            }else{
                Member savedMember = memberRepository.findByUserId(form.getId());
                savedMember.setEmail(form.getEmail());
                savedMember.setPhoneNumber(form.getMobile());
                savedMember.setName(form.getName());
                return memberRepository.save(savedMember);
            }
        }
        return null;
    }

    //validation 검사
    public Boolean error(JoinForm form, BindingResult bindingResult) {
        if (!form.getPassword().equals(form.getPassword2())) {
            bindingResult.addError(new FieldError("member", "password2", "일치하지 않습니다."));
        }
        if (memberRepository.findByUserId(form.getUserId()) != null) {
            bindingResult.addError(new FieldError("member", "userId", "이미 존재하는 아이디입니다."));
        }
        if (memberRepository.findByEmail(form.getEmail()) != null) {
            bindingResult.addError(new FieldError("member", "email", "이미 존재하는 이메일입니다."));
        }
        return bindingResult.hasErrors();
    }

    //이메일 확인 메시지 발송 메서드
    void sendConfirmMail(Member member, MemberToken token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zanygeek8371@xn--oy2b6m82b8p.com");
        message.setTo(member.getEmail());
        message.setSubject(member.getName() + "님, 리멤버미, 가입을 축하합니다.");
        message.setText("메일 확인을 위해 url을 클릭해 주세요: "
                + uri + "join/confirmMail?token=" + token.getConfirmToken());
        try {
            mailSenderService.sendMail(message);
        } catch (Exception e) {
            log.error("에러 발생:" + e);
        }
    }

    //JoinForm <-> Member 전환 메서드
    Member formToMember(JoinForm form) {
        Member member = new Member();
        member.setDate(form.getDate());
        member.setEmail(form.getEmail());
        member.setUserId(form.getUserId());
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setPhoneNumber(form.getPhoneNumber());
        member.setEnabled(false);
        return member;
    }
}

