package gr.personal.story.domain;

import java.util.Date;

/**
 * Created by Nick Kanakis on 11/5/2017.
 */
public class StoryRequest {

    private String id;
    private String story;
    private String title;
    private String userId;
    private Date postDate;
    private Geolocation geoLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Geolocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(Geolocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Override
    public String toString() {
        return "StoryRequest{" +
                "id='" + id + '\'' +
                ", story='" + story + '\'' +
                ", title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                ", postDate=" + postDate +
                ", geolocation=" + geoLocation +
                '}';
    }
}
