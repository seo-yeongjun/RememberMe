package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Photo;
import com.zanygeek.rememberme.repository.MemberRepository;
import com.zanygeek.rememberme.repository.MemorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemorialService {
    @Autowired
    MemorialRepository memorialRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    //기념관 저장, 암호가 있으면 해쉬 암호화
    public Memorial save(Memorial memorial, Member member){
        memorial.setMemberId(member.getId());
        if(memorial.getLocked()){
            memorial.setPassword(passwordEncoder.encode(memorial.getPassword()));
        }
        return memorialRepository.save(memorial);
    }
}