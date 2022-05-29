package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.*;
import com.zanygeek.rememberme.form.EditAlarmForm;
import com.zanygeek.rememberme.repository.AlarmRepository;
import com.zanygeek.rememberme.repository.MemberRepository;
import com.zanygeek.rememberme.repository.MemorialRepository;
import com.zanygeek.rememberme.repository.UnsubscribeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    UnsubscribeRepository unsubscribeRepository;

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

    public List<EditAlarmForm> getAlarms(int memberId) {
        List<Alarm> alarmList = alarmRepository.findAllByMemberId(memberId);
        List<EditAlarmForm> editAlarmForms = new ArrayList<>();
        if (!alarmList.isEmpty()) {
            for (Alarm alarm : alarmList) {
                String name = memorialRepository.getById(alarm.getMemorialId()).getName();
                editAlarmForms.add(new EditAlarmForm(alarm, name));
            }
        }
        return editAlarmForms;
    }

    public void editAlarm(EditAlarmForm editAlarmForm, int memberId) {
        Alarm alarm = new Alarm(editAlarmForm, memberId);
        if (!alarm.isCheckDate() && !alarm.isCheckEvent())
            alarmRepository.delete(alarm);
        else
            alarmRepository.save(alarm);
    }

    @Transactional
    public void sendAlarmMail(Event event) throws MessagingException {
        Memorial memorial = memorialRepository.getById(event.getMemorialId());
        List<Alarm> alarmList = alarmRepository.findAllByMemorialIdAndCheckEventIsTrue(memorial.getId());
        MimeMessage message = mailSenderService.mimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("zanygeek8371@xn--oy2b6m82b8p.com");
        for (Alarm alarm : alarmList) {
            Member member = memberRepository.getById(alarm.getMemberId());
            Unsubscribe unsubscribe = unsubscribeRepository.findByMemberId(member.getId());
            helper.setTo(member.getEmail());
            message.setSubject("[리멤버미] " + memorial.getName() + "님의 새로운 추모 행사가 등록되었습니다.");
            Context context = new Context();
            context.setVariable("name", member.getName());
            context.setVariable("memorialName", memorial.getName());
            context.setVariable("rejectSend", url+"edit/email/unsubscribe?token="+unsubscribe.getToken());
            context.setVariable("url", url);
            context.setVariable("memorialUrl", url+"memorial/"+memorial.getId());
            String html = templateEngine.process("mail/alarm", context);
            helper.setText(html,true);
            try {
                mailSenderService.sendMail(message);
            } catch (Exception e) {
                log.error("에러 발생:" + e);
            }
        }
    }

    public void sendMemorialDate(Memorial memorial) throws MessagingException {
        List<Alarm> alarmList = alarmRepository.findAllByMemorialIdAndCheckDateIsTrue(memorial.getId());

        MimeMessage message = mailSenderService.mimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("zanygeek8371@xn--oy2b6m82b8p.com");

        for (Alarm alarm : alarmList) {
            Member member = memberRepository.getById(alarm.getMemberId());
            Unsubscribe unsubscribe = unsubscribeRepository.findByMemberId(member.getId());
            helper.setTo(member.getEmail());
            helper.setSubject("[리멤버미] " + memorial.getName() + "님의 기일 7일 전입니다.");
            Context context = new Context();
            context.setVariable("name", member.getName());
            context.setVariable("memorialName", memorial.getName());
            context.setVariable("rejectSend", url+"edit/email/unsubscribe?token="+unsubscribe.getToken());
            context.setVariable("url", url);
            context.setVariable("memorialUrl", url+"memorial/"+memorial.getId());
            String html = templateEngine.process("mail/alarm", context);
            helper.setText(html,true);
            try {
                mailSenderService.sendMail(message);
            } catch (Exception e) {
                log.error("에러 발생:" + e);
            }
        }
    }

    @Transactional
    public void rejectMail(String token){
       Unsubscribe unsubscribe = unsubscribeRepository.findByToken(token);
       alarmRepository.deleteAllByMemberId(unsubscribe.getMemberId());
    }

    @Transactional
    public boolean existsAlarmByToken(String token){
        Unsubscribe unsubscribe = unsubscribeRepository.findByToken(token);
        return alarmRepository.existsByMemberId(unsubscribe.getMemberId());
    }
}
