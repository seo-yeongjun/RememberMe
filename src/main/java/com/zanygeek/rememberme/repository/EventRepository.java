package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.Event;
import com.zanygeek.rememberme.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
   List<Event> findAllByMemorialIdOrderByDateDesc(int memorialId);
}
