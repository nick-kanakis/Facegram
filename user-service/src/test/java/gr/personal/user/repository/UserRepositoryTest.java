package gr.personal.user.repository;

import gr.personal.user.domain.Gender;
import gr.personal.user.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 18/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setup(){
        User user = new User();
        user.setFriendIDs(new ArrayList<>());
        user.setGender(Gender.MALE);
        user.setName("Nick");
        user.setSurname("Kanakis");

        Assert.assertNull(user.getUsername());
        userRepository.save(user);
        Assert.assertNotNull(user.getUsername());
    }

    @Test
    public void shouldFetchUser() throws Throwable{
        List<User> users = userRepository.findBySurname("Kanakis");
        Assert.assertNotNull(users);
        Assert.assertEquals("Nick", users.get(0).getName());
    }

    @Test
    public void shouldUpdateUser() throws Throwable{
        List<User> users = userRepository.findBySurname("Kanakis");
        users.get(0).setName("Ilias");
        List<User> usersAfterUpdate = userRepository.findBySurname("Kanakis");
        Assert.assertNotNull(users);
        Assert.assertEquals("Ilias", users.get(0).getName());
    }

    @After
    public void tearDown(){
        userRepository.deleteAll();
    }

}
