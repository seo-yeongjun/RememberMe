package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.Map;
import com.zanygeek.rememberme.entity.SNS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SNSRepository extends JpaRepository<SNS, Integer> {
    List<SNS> findAllByMemorialId(int memorialId);
}
