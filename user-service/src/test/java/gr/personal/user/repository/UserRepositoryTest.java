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
        User user = createUser();

        Assert.assertNull(user.getUsername());
        userRepository.save(user);
        Assert.assertNotNull(user.getUsername());
    }

    @After
    public void tearDown(){
        userRepository.deleteAll();
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


    @Test
    public void shouldReturnNullUser() throws Throwable{
        User user = userRepository.findByUsername("notExistingUsername");
        Assert.assertNull(user);
    }

    @Test
    public void shouldReturnUserByUsername() throws Exception {
        User user = createUser();
        user.setUsername("nicolasmanic");
        userRepository.save(user);

        Assert.assertEquals("nicolasmanic",user.getUsername());

        User nicolasmanic = userRepository.findByUsername("nicolasmanic");

        Assert.assertNotNull(nicolasmanic);
        Assert.assertEquals("nicolasmanic",user.getUsername());

    }

    @Test
    public void shouldReturnListOfUsers() throws Exception {

        List <String> usernames = new ArrayList<>();
        usernames.add("nicolasmanic");

        Iterable<User> friends = userRepository.findAll(usernames);

        for (User user:friends) {
            Assert.assertNotNull(user);
            Assert.assertEquals("nicolasmanic",user.getUsername());

        }

    }

    private User createUser(){
        User user = new User();
        user.setFollowingIds(new ArrayList<>());
        user.setGender(Gender.MALE);
        user.setName("Nick");
        user.setSurname("Kanakis");
        return user;
    }


}
