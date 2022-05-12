package com.zanygeek.rememberme.form;

import com.zanygeek.rememberme.entity.Alarm;
import lombok.Data;

@Data
public class EditAlarmForm {
    private int id;
    private int memorialId;
    private String memorialName;
    private boolean checkEvent;
    private boolean checkDate;

    public EditAlarmForm(Alarm alarm,String memorialName){
        this.id = alarm.getId();
        this.memorialId = alarm.getMemorialId();
        this.checkDate = alarm.isCheckDate();
        this.checkEvent = alarm.isCheckEvent();
        this.memorialName = memorialName;
    }
    public EditAlarmForm(){
    }
}
