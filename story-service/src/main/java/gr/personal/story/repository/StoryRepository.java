package gr.personal.story.repository;

import gr.personal.story.domain.Geolocation;
import gr.personal.story.domain.StoryImpl;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */
public interface StoryRepository extends MongoRepository<StoryImpl, String> {

    StoryImpl findById(String id);

    List<StoryImpl> findByGeolocation(Geolocation geolocation);

    List<StoryImpl> findByUserId(String userId);


}
