package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.*;
import com.zanygeek.rememberme.repository.AlarmRepository;
import com.zanygeek.rememberme.repository.MemberRepository;
import com.zanygeek.rememberme.repository.MemorialRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Log4j2
public class AlarmService {
    @Autowired
    AlarmRepository alarmRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemorialRepository memorialRepository;
    @Value("${rememberme.uri}")
    String url;
    @Autowired
    MailSenderService mailSenderService;

    public void setAlarm(int memberId, int memorialId, Alarm alarm) {
        alarm.setMemberId(memberId);
        alarm.setMemorialId(memorialId);
        if (alarm.isCheckDate() || alarm.isCheckEvent()) {
            alarmRepository.save(alarm);
        } else {
            if (alarmRepository.existsById(alarm.getId())) {
                alarmRepository.delete(alarm);
            }
        }
    }

    public Alarm getAlarm(int memberId, int memorialId) {
        Alarm alarm = alarmRepository.findByMemberIdAndMemorialId(memberId, memorialId);
        if (alarm == null)
            return new Alarm();
        else return alarm;
    }

    public void sendAlarmMail(Event event) {
        Memorial memorial = memorialRepository.getById(event.getMemorialId());
        List<Alarm> alarmList = alarmRepository.findAllByMemorialIdAndCheckEventIsTrue(memorial.getId());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zanygeek8371@xn--oy2b6m82b8p.com");
        for (Alarm alarm : alarmList) {
            Member member = memberRepository.getById(alarm.getMemberId());
            message.setTo(member.getEmail());
            message.setSubject("[리멤버미] " + memorial.getName() + "님의 새로운 추모 행사가 등록되었습니다.");
            message.setText("안녕하세요. "+member.getName() +
                    "님, "+memorial.getName()+"님의 추모행사가 등록되었습니다."
                    +"\n\n행사 제목: " + event.getTitle() + "\n행사 날짜: " + event.getDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")) + "\n자세한 일정은 " + url + "memorial/" + memorial.getId() + " 에서 확인 할 수 있습니다.");
            try {
                mailSenderService.sendMail(message);
            } catch (Exception e) {
                log.error("에러 발생:" + e);
            }
        }
    }

    public void sendMemorialDate(Memorial memorial,String deathDate){
        List<Alarm> alarmList = alarmRepository.findAllByMemorialIdAndCheckDateIsTrue(memorial.getId());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zanygeek8371@xn--oy2b6m82b8p.com");
        for (Alarm alarm : alarmList) {
            Member member = memberRepository.getById(alarm.getMemberId());
            message.setTo(member.getEmail());
            message.setSubject("[리멤버미] " + memorial.getName() + "님의 기일 7일 전입니다.");
            message.setText("안녕하세요. "+member.getName() +
                    "님, "+memorial.getName()+"님의 설정하신 알람에 따라, "+memorial.getName()+"님의"
                    +"기일인 "+deathDate+"의 7일전임을 알리는 메일을 발송드립니다.\n"
             +memorial.getName()+"님의 리멤버미 추모공간: "+ url + "memorial/" + memorial.getId());
            try {
                mailSenderService.sendMail(message);
            } catch (Exception e) {
                log.error("에러 발생:" + e);
            }
        }
    }
}
