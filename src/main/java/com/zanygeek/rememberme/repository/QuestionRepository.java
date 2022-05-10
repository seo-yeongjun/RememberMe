package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.Alarm;
import com.zanygeek.rememberme.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
