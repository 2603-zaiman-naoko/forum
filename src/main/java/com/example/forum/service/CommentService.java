package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public void saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentEntity(reqComment);
        commentRepository.save(saveComment);
    }

    private Comment setCommentEntity(CommentForm reqComment) {
        Comment comment = new Comment();
/*自動採番に任せるので書かない
        comment.setId(reqComment.getId());
*/
        // 画面から渡ってきたIDをreport_idに入れる
        comment.setReportId(reqComment.getReportId());
        comment.setText(reqComment.getText());
        return comment;
    }

}
