package com.example.dogstar.repository;

import com.example.dogstar.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByEmail(String email);
}
