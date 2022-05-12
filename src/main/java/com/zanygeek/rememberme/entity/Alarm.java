package com.zanygeek.rememberme.entity;

import com.zanygeek.rememberme.form.EditAlarmForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int memorialId;
    private boolean checkEvent;
    private boolean checkDate;
    private int memberId;

    public Alarm(){

    }
    public Alarm(EditAlarmForm editAlarmForm,int memberId){
        this.id= editAlarmForm.getId();
        this.memorialId = editAlarmForm.getMemorialId();
        this.checkDate = editAlarmForm.isCheckDate();
        this.checkEvent = editAlarmForm.isCheckEvent();
        this.memberId = memberId;
    }
}
