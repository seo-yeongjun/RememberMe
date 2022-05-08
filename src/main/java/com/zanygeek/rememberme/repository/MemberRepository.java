package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByEmail(String email);
    Member findByUserId(String userId);
    void deleteByEnabledIsFalseAndUserId(String userId);
    boolean existsByUserId(String userId);
}
