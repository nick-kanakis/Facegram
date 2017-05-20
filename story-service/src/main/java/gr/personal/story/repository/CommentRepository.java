package gr.personal.story.repository;

import gr.personal.story.domain.Comment;

/**
 * Created by Nick Kanakis on 20/5/2017.
 */
public interface CommentRepository {

    Comment findCommentById(String commentId);

    void deleteCommentById(String commentId);
}
