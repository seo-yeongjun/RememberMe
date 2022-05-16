package com.zanygeek.rememberme;

import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.MemberToken;
import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.repository.MemberRepository;
import com.zanygeek.rememberme.repository.MemberTokenRepository;
import com.zanygeek.rememberme.repository.MemorialRepository;
import com.zanygeek.rememberme.service.AlarmService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
@EnableScheduling
@Log4j2
public class Schedules {
    @Autowired
    MemberTokenRepository memberTokenRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemorialRepository memorialRepository;
    @Autowired
    AlarmService alarmService;

    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void reSet() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -10);
        Date date = cal.getTime();
        List<MemberToken> expirationTokens = memberTokenRepository.findAllByCreatedDateLessThan(date);
        try {
            for (MemberToken token : expirationTokens) {
                Member member = memberRepository.findByUserId(token.getUserId());
                memberRepository.deleteByEnabledIsFalseAndUserId(member.getUserId());
            }
            memberTokenRepository.deleteAllByCreatedDateLessThan(date);
        } catch (Exception e) {
            log.info("에러 발생:" + e);
        }
    }

    @Scheduled(cron = "0 15 9 * * ?")
    @Transactional
    public void sendMemorialDateMail() throws ParseException, MessagingException {
        List<Memorial> memorials = memorialRepository.findAll();
        for(Memorial memorial : memorials){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
            SimpleDateFormat dateFormatKO = new SimpleDateFormat("MM월 dd일");
            Calendar day = Calendar.getInstance();
            day.setTime(memorial.getDeathDate());
            String deathDate = dateFormatKO.format(day.getTime());
            day.add(Calendar.DATE , -7);
            String goalDate = dateFormat.format(day.getTime());
            String today = dateFormat.format(new Date());
            if(goalDate.equals(today)){
                alarmService.sendMemorialDate(memorial);
            }
        }
    }
}
