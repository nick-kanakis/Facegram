package gr.personal.group.repository;

import gr.personal.group.domain.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Nick Kanakis on 17/6/2017.
 */
public interface GroupRepository extends MongoRepository<Group,String> {
}
