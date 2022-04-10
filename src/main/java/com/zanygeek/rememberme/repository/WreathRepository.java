package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.Member;
import com.zanygeek.rememberme.entity.Wreath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WreathRepository extends JpaRepository<Wreath, Integer> {
    List<Wreath> findAllByMemorialIdOrderByDateDesc(int memorialId);
}
