package com.project.thisvsthat.post.dao;

import com.project.thisvsthat.common.dto.PostDTO;
import com.project.thisvsthat.common.entity.Post;
import com.project.thisvsthat.common.entity.User;
import com.project.thisvsthat.common.enums.Category;
import com.project.thisvsthat.common.enums.PostStatus;
import com.project.thisvsthat.common.enums.VoteStatus;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Transactional
public class PostDAO {
    @Autowired
    EntityManager em;

    public void savePost(Long userId, Post post) {
        System.out.println("service 에서 받아온 fromDto 정보 ========== "+post);

        User user = em.find(User.class, userId);
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);
        user.getPosts().add(post);

        System.out.println("저장 전 post 결과 (유저아이디, 제목, 카테고리, 해시태그) ========== "+ post.getUser().getUserId() + post.getTitle() + post.getCategory() + post.getHashtags());
        em.persist(post);
    }

    public Post findOnePost(long postId) {
        Post post = em.find(Post.class, postId);
        return post;
    }

    public void updatePost(Long postId, PostDTO dto) {
        Post post = em.find(Post.class, postId);

        post.setUpdatedAt(LocalDateTime.now());
        post.setCategory(dto.getCategory());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setHashtags(dto.getHashtags());
        post.setOption1(dto.getOption1());
        post.setOption2(dto.getOption2());
        post.setOption1ImageUrl(dto.getOption1ImageUrl());
        post.setOption2ImageUrl(dto.getOption2ImageUrl());

        System.out.println("updatePost 후 post 결과 (제목, 카테고리)" + post.getTitle() + post.getCategory());
    }

    public void deletePost(Long postId) {
        Post post = em.find(Post.class, postId);
        post.setPostStatus(PostStatus.DELETED);
    }
}
