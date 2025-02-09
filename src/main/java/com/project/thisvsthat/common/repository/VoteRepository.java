package com.project.thisvsthat.common.repository;

import com.project.thisvsthat.common.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    // 투표 관련 추가 쿼리 메서드가 필요하면 여기에 작성
}