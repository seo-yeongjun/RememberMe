package com.zanygeek.rememberme.controller;

import com.zanygeek.rememberme.entity.Alarm;
import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Wreath;
import com.zanygeek.rememberme.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("memorial")
public class AlarmController {
    @Autowired
    AlarmService alarmService;

    @PostMapping("{memorialId}/alarm")
    public String addAlarm(@PathVariable int memorialId, @SessionAttribute(name = "member", required = false) Member member,
                           @ModelAttribute Alarm alarm, @RequestParam(required = false, defaultValue = "") String redirectURL) {
        alarmService.setAlarm(member.getId(),memorialId,alarm);
        if (redirectURL.isEmpty())
            return "redirect:/";
        else
            return "redirect:" + redirectURL;
    }
}
