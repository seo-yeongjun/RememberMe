package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.Map;
import com.zanygeek.rememberme.entity.SNS;
import com.zanygeek.rememberme.entity.XY;
import com.zanygeek.rememberme.form.SNSForm;
import com.zanygeek.rememberme.repository.MapRepository;
import com.zanygeek.rememberme.repository.SNSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SNSService {
    @Autowired
    SNSRepository snsRepository;

    public SNSForm getSNSForm(int memorialId) {
        List<SNS> savedSNS = snsRepository.findAllByMemorialId(memorialId);
        SNSForm form = new SNSForm();
        form.setSns1(new SNS());
        form.setSns2(new SNS());
        if (!savedSNS.isEmpty()) {
            form.setSns1(savedSNS.get(0));
            form.setSns2(savedSNS.get(1));
        }
        return form;
    }

    public List<SNS> getSNSList(int memorialId) {
        return snsRepository.findAllByMemorialId(memorialId);
    }

    public void saveSNSForm(int memorialId, SNSForm snsForm) {
        snsForm.getSns1().setMemorialId(memorialId);
        snsForm.getSns2().setMemorialId(memorialId);
        if (snsForm.getSns1().getUrl().equals("")&&snsForm.getSns1().getId() != 0) {
                snsRepository.delete(snsForm.getSns1());
        } else if(!snsForm.getSns1().getUrl().equals(""))
            snsRepository.save(snsForm.getSns1());

        if (snsForm.getSns2().getUrl().equals("")&&snsForm.getSns2().getId() != 0) {
            snsRepository.delete(snsForm.getSns2());
        } else if(!snsForm.getSns2().getUrl().equals(""))
            snsRepository.save(snsForm.getSns2());

    }

}
