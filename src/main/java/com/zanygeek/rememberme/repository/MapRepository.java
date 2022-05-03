package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.Map;
import com.zanygeek.rememberme.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<Map, Integer> {
    Map findByMemorialId(int memorialId);
}
