package gr.personal.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 15/5/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HomepageServiceTest {

    @Autowired
    HomepageService homepageService;

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveNewStories() throws Exception {
        homepageService.retrieveNewStories("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveHotStories() throws Exception {
        homepageService.retrieveHotStories("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveTopStories() throws Exception {
        homepageService.retrieveTopStories("", null);
    }
}
