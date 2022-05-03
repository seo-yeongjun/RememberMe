package com.zanygeek.rememberme.service;

import com.zanygeek.rememberme.entity.Map;
import com.zanygeek.rememberme.entity.XY;
import com.zanygeek.rememberme.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapService {
    @Autowired
    AddressApiService addressApiService;
    @Autowired
    MapRepository mapRepository;

    public void saveMap(Map map, int memorialId) {
        map.setMemorialId(memorialId);
        XY xy = addressApiService.getXY(map.getAddress());
        map.setX(xy.getX());
        map.setY(xy.getY());
        mapRepository.save(map);
    }
    public Map getMap(int memorialId){
        return mapRepository.findByMemorialId(memorialId);
    }
}
