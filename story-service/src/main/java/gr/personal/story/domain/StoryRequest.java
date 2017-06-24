package gr.personal.story.domain;

import java.io.Serializable;

/**
 * Created by Nick Kanakis on 11/5/2017.
 */
public class StoryRequest implements Serializable{

    private static final long serialVersionUID = 1L;
    private String story;
    private String title;
    private String groupId;
    private Geolocation geolocation;

    public String getStory() {
        return story;
    }
    public String getTitle() {
        return title;
    }
    public Geolocation getGeolocation() {
        return geolocation;
    }
    public String getGroupId() {
        return groupId;
    }

    public StoryRequest(String story, String title, String groupId, Geolocation geolocation) {
        this.story = story;
        this.title = title;
        this.groupId = groupId;
        this.geolocation = geolocation;
    }

    public StoryRequest() {
    }

    @Override
    public String toString() {
        return "StoryRequest{" +
                "story='" + story + '\'' +
                ", title='" + title + '\'' +
                ", groupId='" + groupId + '\'' +
                ", geolocation=" + geolocation +
                '}';
    }

}
