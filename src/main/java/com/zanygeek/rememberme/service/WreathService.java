package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.Wreath;
import com.zanygeek.rememberme.repository.WreathRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class WreathService {
    @Autowired
    WreathRepository wreathRepository;

    public void saveWreath(Wreath wreath, int memorialId){
        wreath.setMemorialId(memorialId);
        wreath.setDate(LocalDateTime.now());
        wreath.setText(wreath.getText().replaceAll("\r\n","<br>"));
        try{
        wreathRepository.save(wreath);}
        catch (Exception e){
            log.error("에러 발생:", e);
        }
    }
    public List<Wreath> getWreaths(int memorialId){
        return wreathRepository.findAllByMemorialIdOrderByIdDesc(memorialId);
    }
}
