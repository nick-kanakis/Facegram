package gr.personal.story.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewStoriesServiceTest {

    @Autowired
    private NewStoriesService newStoriesService;

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetNewStoriesOfGroup(){
        newStoriesService.getStoriesOfGroup("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetNewStoriesOfLocation(){
        newStoriesService.getStoriesOfLocation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetNewStoriesOfUser(){
        newStoriesService.getStoriesOfUser("");
    }


}
