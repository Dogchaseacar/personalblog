package com.wdy.blog.web;

import com.wdy.blog.po.Comment;
import com.wdy.blog.service.BlogService;
import com.wdy.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

//    @Value("${comment.avatar}")
    private String avatar = "/images/fig.jpg";

    @Transactional
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        List<Comment> commentList = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", commentList);
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments/"+ blogId;
    }

}
