package gr.personal.user.repository;

import gr.personal.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by nkanakis on 5/18/2017.
 */
public interface UserRepository extends MongoRepository<User,String> {
}
