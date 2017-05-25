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

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
