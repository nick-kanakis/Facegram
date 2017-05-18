package gr.personal.story.repository;

import gr.personal.story.domain.GroupStory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */
public interface GroupStoryRepository extends MongoRepository<GroupStory, String> {

     List<GroupStory> findByGroupId(String groupId);
}
