package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.Wreath;
import com.zanygeek.rememberme.repository.WreathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class WreathService {
    @Autowired
    WreathRepository wreathRepository;

    public void saveWreath(Wreath wreath, int memorialId){
        wreath.setMemorialId(memorialId);
        wreath.setDate(LocalDateTime.now());
        wreath.setText(wreath.getText().replaceAll("\r\n","<br>"));
        wreathRepository.save(wreath);
    }
}
