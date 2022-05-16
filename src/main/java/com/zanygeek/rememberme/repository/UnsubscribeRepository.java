package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.Unsubscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UnsubscribeRepository extends JpaRepository<Unsubscribe, Integer> {
    Unsubscribe findByMemberId(int memberId);
    Unsubscribe findByToken(String token);
}
