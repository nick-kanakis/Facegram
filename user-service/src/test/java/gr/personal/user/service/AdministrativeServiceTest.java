package gr.personal.user.service;

import gr.personal.user.client.AuthClient;
import gr.personal.user.client.StoryClient;
import gr.personal.user.domain.RegistrationUser;
import gr.personal.user.domain.User;
import gr.personal.user.domain.UserRequest;
import gr.personal.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static gr.personal.user.helper.FakeDataGenerator.generateUser;
import static gr.personal.user.helper.FakeDataGenerator.generateUserRequest;
import static gr.personal.user.helper.FakeDataGenerator.generateUsers;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Nick Kanakis on 15/5/2017.
 */
@RunWith(SpringRunner.class)
public class AdministrativeServiceTest {

    @Autowired
    private AdministrativeService administrativeService;

    @TestConfiguration
    static class AdministrativeTestContextConfiguration{

        @Bean
        public AdministrativeService administrativeService(){
            return new AdministrativeServiceImpl();
        }
        @Bean
        public CacheManager cacheManager(){
            return new ConcurrentMapCacheManager("testCache");
        }
    }

    @MockBean
    private AuthClient authClient;

    @MockBean
    private UserRepository userRepository;

    private User originalUser;
    private List<User> originalUsers;

    @Before
    public void setUp() throws Exception {

        originalUser = generateUser();
        originalUsers = generateUsers();


        when(userRepository.findAll()).thenReturn(originalUsers);
        when(userRepository.findAll(anyCollection())).thenReturn(originalUsers);
        when(userRepository.exists("testUsername")).thenReturn(true);
        when(userRepository.exists("testFollowingUsername")).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(originalUser);
        when(userRepository.save(any(User.class))).thenReturn(originalUser);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        UserRequest userRequest = generateUserRequest();
        String result = administrativeService.createUser(userRequest);
        assertEquals(result, "OK");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreateUser(){
        administrativeService.createUser(null);
    }

    @Test
    public void shouldUpdateUser(){
        UserRequest userRequest = generateUserRequest();
        String result = administrativeService.updateUser(userRequest);
        assertEquals(result, "OK");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUpdateUser(){
        administrativeService.updateUser(null);
    }

    @Test
    public void shouldDeleteUser(){
        String result = administrativeService.deleteUser("testUsername");
        assertEquals(result, "OK");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToDeleteUser(){
        administrativeService.deleteUser("");
    }

    @Test
    public void shouldRetrieveUser(){
        User user = administrativeService.retrieveUser("testUsername");
        assertEquals(user, originalUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveUser(){
        administrativeService.retrieveUser("");
    }

    @Test
    public void shouldAddFollowing(){
        String result = administrativeService.addFollowing("testUsername", "testFollowingUsername");
        assertEquals(result, "OK");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToAddFollowing(){
        administrativeService.addFollowing("",null);
    }

    @Test
    public void shouldRemoveFollowing(){
        String result = administrativeService.removeFollowing("testUsername", "testFollowingUsername");
        assertEquals(result, "OK");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRemoveFollowing(){
        administrativeService.removeFollowing("","");
    }

    @Test
    public void shouldRetrieveFollowings(){
        List<User> followings = administrativeService.retrieveFollowings("testUsername");
        assertEquals(followings, originalUsers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveFollowings(){
        administrativeService.retrieveFollowings("");
    }
}
