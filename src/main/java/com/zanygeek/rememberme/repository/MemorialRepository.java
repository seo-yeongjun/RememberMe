package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.Memorial;
import com.zanygeek.rememberme.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemorialRepository extends JpaRepository<Memorial, Integer> {
}
