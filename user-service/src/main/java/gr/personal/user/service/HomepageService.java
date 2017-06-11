package gr.personal.user.service;

import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;

import java.util.List;

/**
 * Created by Nick Kanakis on 29/5/2017.
 */
public interface HomepageService {

    List<Story> retrieveNewStories(String username, Geolocation geolocation);
    List<Story> retrieveHotStories(String username, Geolocation geolocation);
    List<Story> retrieveTopStories(String username, Geolocation geolocation);
    List<Story> mergeAndRemoveDuplicates(List<Story> list1, List<Story> list2);
    List<Story> retrieveMyStories(String username);
}
