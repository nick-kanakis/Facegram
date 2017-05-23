package gr.personal.story.repository;

import gr.personal.story.domain.Comment;
import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by Nick Kanakis on 20/5/2017.
 */
public class StoryRepositoryImpl implements CustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final double COORDINATES_RANGE = 0.2;

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

    @Override
    public List<Story> findNewStoriesOfUser(String userId) {

        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "postDate"));
        query.addCriteria(Criteria.where("userId").is(userId));

        return mongoTemplate.find(query, Story.class);

    }

    @Override
    public List<Story> findNewStoriesOfLocation(Geolocation geolocation) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "postDate"));
        query.addCriteria(Criteria.where("geolocation.longitude").gte(geolocation.getLongitude()- COORDINATES_RANGE).lte(geolocation.getLongitude()+ COORDINATES_RANGE));
        query.addCriteria(Criteria.where("geolocation.latitude").gte(geolocation.getLatitude()- COORDINATES_RANGE).lte(geolocation.getLatitude()+ COORDINATES_RANGE));

        return mongoTemplate.find(query, Story.class);
    }

    @Override
    public List<Story> findNewStoriesOfGroup(String groupId) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "postDate"));
        query.addCriteria(Criteria.where("groupId").is(groupId));

        return mongoTemplate.find(query, Story.class);
    }

    @Override
    public List<Story> findTopStoriesOfLocation(Geolocation geolocation) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "likes"));
        query.addCriteria(Criteria.where("geolocation.longitude").gte(geolocation.getLongitude()- COORDINATES_RANGE).lte(geolocation.getLongitude()+ COORDINATES_RANGE));
        query.addCriteria(Criteria.where("geolocation.latitude").gte(geolocation.getLatitude()- COORDINATES_RANGE).lte(geolocation.getLatitude()+ COORDINATES_RANGE));

        return mongoTemplate.find(query, Story.class);
    }

    @Override
    public List<Story> findTopStoriesOfUser(String userId) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "likes"));
        query.addCriteria(Criteria.where("userId").is(userId));

        return mongoTemplate.find(query, Story.class);
    }

    @Override
    public List<Story> findTopStoriesOfGroup(String groupId) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "likes"));
        query.addCriteria(Criteria.where("groupId").is(groupId));

        return mongoTemplate.find(query, Story.class);
    }

    @Override
    public List<Story> findHotStoriesOfGroup(String groupId) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "this.comments.length"));
        query.addCriteria(Criteria.where("groupId").is(groupId));

        return mongoTemplate.find(query, Story.class);
    }

    @Override
    public List<Story> findHotStoriesOfLocation(Geolocation geolocation) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "this.comments.length"));
        query.addCriteria(Criteria.where("geolocation.longitude").gte(geolocation.getLongitude()- COORDINATES_RANGE).lte(geolocation.getLongitude()+ COORDINATES_RANGE));
        query.addCriteria(Criteria.where("geolocation.latitude").gte(geolocation.getLatitude()- COORDINATES_RANGE).lte(geolocation.getLatitude()+ COORDINATES_RANGE));

        return mongoTemplate.find(query, Story.class);
    }

    @Override
    public List<Story> findHotStoriesOfUser(String userId) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "this.comments.length"));
        query.addCriteria(Criteria.where("userId").is(userId));

        return mongoTemplate.find(query, Story.class);
    }
}
