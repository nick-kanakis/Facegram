package gr.personal.user.repository;

import gr.personal.user.domain.User;
import gr.personal.user.service.AdministrativeService;
import gr.personal.user.service.AdministrativeServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static gr.personal.user.helper.FakeDataGenerator.generateUser;

/**
 * Created by Nick Kanakis on 18/5/2017.
 */

@RunWith(SpringRunner.class)
@DataMongoTest
@ActiveProfiles("repository")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private User user;

    @Before
    public void setup(){
        user = generateUser();

        userRepository.save(user);
        Assert.assertNotNull(user.getUsername());
    }

    @TestConfiguration
    static class UserRepositoryTestContextConfiguration{

        @Bean
        public ResourceServerProperties resourceServerProperties(){
            return new ResourceServerProperties();
        }

    }


    @After
    public void tearDown(){
        userRepository.delete(user.getUsername());
    }


    @Test
    public void shouldFetchUser() throws Throwable{
        User retrievedUser = userRepository.findByUsername(user.getUsername());
        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals(user.getName(), retrievedUser.getName());
    }

    @Test
    public void shouldUpdateUser() throws Throwable{
        user.setName("Ilias");
        userRepository.save(user);
        User retrievedUser = userRepository.findByUsername(user.getUsername());
        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals(user.getName(), retrievedUser.getName());
    }


    @Test
    public void shouldReturnNullUser() throws Throwable{
        User user = userRepository.findByUsername("notExistingUsername");
        Assert.assertNull(user);
    }

    @Test
    public void shouldReturnUserByUsername() throws Exception {
        User retrievedUser = userRepository.findByUsername(user.getUsername());
        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals(retrievedUser.getUsername(),user.getUsername());

    }

}
