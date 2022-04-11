package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.Obituary;
import com.zanygeek.rememberme.entity.Wreath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObituaryRepository extends JpaRepository<Obituary, Integer> {
    List<Obituary> findAllByMemorialIdOrderByIdDesc(int memorialId);
}
