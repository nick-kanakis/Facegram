package gr.personal.auth.service;

import gr.personal.auth.domain.User;
import gr.personal.auth.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;

/**
 * Created by Nick Kanakis on 4/6/2017.
 */
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private User user;


    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        user = new User("testUsername", "testPassword");
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
        Mockito.when(userRepository.findByUsername("myUsername")).thenReturn(null);
    }

    @Test
    public void shouldCreateUser() throws Exception{
        userService.create(new User("myUsername","myPass"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateUser() throws Exception{
        userService.create(new User("myUsername2","myPass2"));
    }
    @Test
    public void shouldLoadUserByUsername() throws Exception{
        UserDetails retrievedUser = userService.loadUserByUsername(user.getUsername());
        assertThat(retrievedUser,is(user));
    }
}
