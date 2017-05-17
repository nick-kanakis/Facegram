package gr.personal.story.repository;

import gr.personal.story.domain.GroupStoryImpl;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */
public interface GroupStoryRepository extends MongoRepository<GroupStoryImpl, String> {

     List<GroupStoryImpl> findByGroupId(String groupId);
}
