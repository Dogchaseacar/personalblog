package com.wdy.blog.service;

import com.wdy.blog.dao.CommentRepository;
import com.wdy.blog.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        return commentRepository.findByBlogId(blogId,Sort.by(Sort.Direction.DESC,"createTime"));
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        } else{
            comment.setParentComment(null);
        }
        comment.setCreatTime(new Date());
        return commentRepository.save(comment);
    }
}
