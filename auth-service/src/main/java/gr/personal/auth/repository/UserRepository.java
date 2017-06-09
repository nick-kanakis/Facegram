package gr.personal.auth.repository;

import gr.personal.auth.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by Nick Kanakis on 3/6/2017.
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
