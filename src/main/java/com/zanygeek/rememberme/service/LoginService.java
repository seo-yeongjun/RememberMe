package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.form.LoginForm;
import com.zanygeek.rememberme.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class LoginService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    //validation 검사
    public Boolean error(LoginForm form, BindingResult bindingResult) {
        Member member = memberRepository.findByUserId(form.getUserId());
        if (member == null) {
            bindingResult.addError(new FieldError("member", "userId", "존재 하지 않는 아이디 입니다.\n"));
        }
        else if (!passwordEncoder.matches(form.getPassword(), member.getPassword())) {
            bindingResult.addError(new FieldError("member", "password", "비밀번호가 틀렸습니다.\n"));
        }
        return bindingResult.hasErrors();
    }

    public Member login(String userId, String password) {
        Member member = memberRepository.findByUserId(userId);
        if (passwordEncoder.matches(password, member.getPassword()))
            return member;
        else
            return null;
    }
}
