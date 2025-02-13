package com.project.thisvsthat.post.service;

import com.project.thisvsthat.common.dto.PostDTO;
import com.project.thisvsthat.common.entity.Post;
import com.project.thisvsthat.post.dao.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    PostDAO dao;

    public void savePost(Long userId, PostDTO dto) {
        dao.savePost(userId, PostDTO.fromDto(dto));
    }

    public PostDTO findOnePost(Long postId) {
        Post post = dao.findOnePost(postId);
        return PostDTO.fromEntity(post);
    }

    public void updatePost(Long postId, PostDTO dto) {
        dao.updatePost(postId, dto);
    }

    public void deletePost(Long postId) {
        dao.deletePost(postId);
    }
}
