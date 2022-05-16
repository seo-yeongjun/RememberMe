package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.Alarm;
import com.zanygeek.rememberme.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    Alarm findByMemberIdAndMemorialId(int memberId, int memorialId);
    List<Alarm> findAllByMemorialIdAndCheckEventIsTrue(int memorialId);
    List<Alarm> findAllByMemorialIdAndCheckDateIsTrue(int memorialId);
    List<Alarm> findAllByMemberId(int memberId);
    void deleteAllByMemberId(int memberId);
    boolean existsByMemberId(int memberId);
}
