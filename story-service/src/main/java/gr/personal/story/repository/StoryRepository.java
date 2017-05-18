package gr.personal.story.repository;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.Story;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */
public interface StoryRepository extends MongoRepository<Story, String> {

    Story findById(String id);

    List<Story> findByGeolocation(Geolocation geolocation);

    List<Story> findByUserId(String userId);


}
