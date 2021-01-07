package com.teamgorm.projectforum.service.impl;

import com.teamgorm.projectforum.exception.CustomizeException;
import com.teamgorm.projectforum.exception.ErrorCode;
import com.teamgorm.projectforum.model.Comment;
import com.teamgorm.projectforum.model.Post;
import com.teamgorm.projectforum.repository.CommentRepository;
import com.teamgorm.projectforum.repository.PostRepository;
import com.teamgorm.projectforum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Comment Service
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getByPostId(String id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return commentRepository.findAllByPostId(id);
        }
        return null;
    }

    @Override
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment getById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CustomizeException(ErrorCode.COMMENT_NOT_FOUND, id));
    }

    @Override
    public Comment update(String id, Comment comment) {
        if (commentRepository.existsById(id)) {
            // Overwrites the comment's id if doesn't match with id
            comment.setId(id);
            return commentRepository.save(comment);
        } else {
            throw new CustomizeException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
