package gr.personal.story.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotStoriesServiceTest {

    @InjectMocks
    private HotStoriesService hotStoriesService;

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetHotStoriesOfGroup(){
        hotStoriesService.getHotStoriesOfGroup("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetHotStoriesOfLocation(){
        hotStoriesService.getHotStoriesOfLocation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGetHotStoriesOfUser(){
        hotStoriesService.getHotStoriesOfUser("");
    }


}
