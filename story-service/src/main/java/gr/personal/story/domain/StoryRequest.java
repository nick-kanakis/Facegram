package gr.personal.story.domain;

/**
 * Created by Nick Kanakis on 11/5/2017.
 */
public class StoryRequest {

    private String story;
    private String title;
    private String userId;
    private String groupId;
    private Geolocation geolocation;

    public String getStory() {
        return story;
    }
    public String getTitle() {
        return title;
    }
    public String getUserId() {
        return userId;
    }
    public Geolocation getGeolocation() {
        return geolocation;
    }
    public String getGroupId() {
        return groupId;
    }

    public StoryRequest(String story, String title, String userId, String groupId, Geolocation geolocation) {
        this.story = story;
        this.title = title;
        this.userId = userId;
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
                ", userId='" + userId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", geolocation=" + geolocation +
                '}';
    }

}
