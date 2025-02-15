package com.project.thisvsthat.common.repository;

import com.project.thisvsthat.common.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT " +
            "SUM(CASE WHEN v.selectedOption = 'OPTION_1' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN v.selectedOption = 'OPTION_2' THEN 1 ELSE 0 END) " +
            "FROM Vote v WHERE v.post.id = :postId")
    Object[] countVotesByPost(@Param("postId") Long postId);
}