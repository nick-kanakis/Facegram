package gr.personal.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 15/5/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministrativeServiceTest {

    @InjectMocks
    private AdministrativeService administrativeService;

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreateUser(){
        administrativeService.createUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUpdateUser(){
        administrativeService.updateUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToDeleteUser(){
        administrativeService.deleteUser("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveUser(){
        administrativeService.retrieveUser("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToAddFollower(){
        administrativeService.addFollower("",null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRemoveFollower(){
        administrativeService.removeFollower("","");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveFollowers(){
        administrativeService.retrieveFollowers("");
    }
}
