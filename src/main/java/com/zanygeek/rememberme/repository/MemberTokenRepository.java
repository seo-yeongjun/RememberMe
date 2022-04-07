package com.zanygeek.rememberme.repository;

import com.zanygeek.rememberme.entity.MemberToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MemberTokenRepository extends JpaRepository<MemberToken, Integer> {
    MemberToken findByConfirmToken(String confirmToken);
    void deleteAllByCreatedDateLessThan(Date date);
    List<MemberToken> findAllByCreatedDateLessThan(Date date);

}
