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
public class HomepageServiceTest {

    @InjectMocks
    HomepageService homepageService;

    @Test
    public void shouldFailToRetrieveNewStories() throws Exception {
        homepageService.retrieveNewStories("", null);
    }

    @Test
    public void shouldFailToRetrieveHotStories() throws Exception {
        homepageService.retrieveHotStories("", null);
    }

    @Test
    public void shouldFailToRetrieveTopStories() throws Exception {
        homepageService.retrieveTopStories("", null);
    }
}
