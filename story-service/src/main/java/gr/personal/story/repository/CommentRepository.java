package gr.personal.story.repository;

import gr.personal.story.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */
public interface CommentRepository extends MongoRepository<Comment, String >{

    Comment findById(String id);

    List<Comment> findByUserId(String userId);

}
