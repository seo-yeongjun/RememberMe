package com.zanygeek.rememberme;

import com.zanygeek.rememberme.repository.MemberTokenRepository;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;


@Component
@EnableScheduling
@Log4j2
public class Schedules {
    @Autowired
    MemberTokenRepository memberTokenRepository;

    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void reSet() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -10);
        Date date = cal.getTime();
        try{
            memberTokenRepository.deleteAllByCreatedDateLessThan(date);}
        catch (Exception e){
            log.info("에러 발생:"+e);
        }
        log.info("시간 만료 memberToken 삭제 : "+date);
    }
}
