package gr.personal.story.repository;

import gr.personal.story.domain.Comment;
import gr.personal.story.domain.Story;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by Nick Kanakis on 20/5/2017.
 */
public class StoryRepositoryImpl implements CommentRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Comment findCommentById(String commentId) {
        // Ids are not queried like other object you have to query them by ObjectId and not String!!
        Story story = mongoTemplate.findOne(new Query(Criteria.where("comments.id").is(new ObjectId(commentId))), Story.class);

        return story.getCommentById(commentId);
    }

    @Override
    public void deleteCommentById(String commentId) {
        Story story = mongoTemplate.findOne(new Query(Criteria.where("comments.id").is(new ObjectId(commentId))), Story.class);
        story.deleteCommentById(commentId);
        mongoTemplate.save(story);
    }
}
