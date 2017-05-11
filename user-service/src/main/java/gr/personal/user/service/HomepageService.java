package gr.personal.user.service;

import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nick Kanakis on 11/5/2017.
 */
@Service
public class HomepageService {
    public List<Story> retrieveNewStories(String userId, Geolocation geolocation) {
        return null;
    }

    public List<Story> retrieveHotStories(String userId, Geolocation geolocation) {
        return null;
    }

    public List<Story> retrieveTopStories(String userId, Geolocation geolocation) {
        return null;
    }
}
