package gr.personal.auth.repository;

import gr.personal.auth.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nick Kanakis on 4/6/2017.
 */
@RunWith(SpringRunner.class)
@DataMongoTest
@ActiveProfiles("repository")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User user;

    @Before
    public void setUp() throws Exception {
        user = new User("testUsername", "testPassword", new ArrayList<>());
        userRepository.save(user);
    }

    @Test
    public void shouldFetchUserByUsername() throws Exception {
        User retrievedUser = userRepository.findByUsername(user.getUsername());

        assertEquals(user, retrievedUser);

    }
}
