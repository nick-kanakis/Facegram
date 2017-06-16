package gr.personal.user.repository;

import gr.personal.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Created by nkanakis on 6/16/2017.
 */

@Component
public class UserRepositoryImpl implements CustomUserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    /*
    * getGroupIdsByUsername will return a User will only followingGroupIds field instantiated.
    * */
    @Override
    public User getGroupIdsByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        query.fields().include("followingGroupIds");
        return mongoTemplate.findOne(query, User.class);
    }
}
