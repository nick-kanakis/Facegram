package gr.personal.group.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */
public class Story extends AbstractEntity{

    private String story;
    private String title;
    private long likes;
    private long unlikes;
    private String groupId;
    private List<Comment> comments;
    private Geolocation geolocation;

    public Story() {
    }

    public static class Builder<T extends Builder>{
        private String id;
        private String title;
        private String userId;
        private Geolocation geolocation;
        private String story;
        private String groupId;


        public Builder geolocation(Geolocation geolocation) {
            this.geolocation = geolocation;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder story(String story) {
            this.story = story;
            return this;
        }

        public Builder groupId(String groupId) {
            this.groupId = groupId;
            return this;
        }
        public Story build(){
            return new Story(this);
        }

    }

    protected Story(Builder builder){
        this.title = builder.title;
        setUserId(builder.userId);
        setPostDate(LocalDateTime.now());
        this.likes = 0;
        this.unlikes = 0;
        this.comments = new ArrayList<>();
        this.geolocation = builder.geolocation;
        setId(builder.id);
        this.story = builder.story;
        this.groupId = builder.groupId;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public long getLikes() {
        return likes;
    }

    public long getUnlikes() {
        return unlikes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getTitle() {
        return title;
    }

    public String getStory() {
        return story;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id='" + getId() + '\'' +
                ", story='" + story + '\'' +
                ", title='" + title + '\'' +
                ", userId='" + getUserId() + '\'' +
                ", postDate=" + getPostDate() +
                ", likes=" + likes +
                ", unlikes=" + unlikes +
                ", groupId='" + groupId + '\'' +
                ", comments=" + comments +
                ", geolocation=" + geolocation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Story story = (Story) o;

        return getId().equals(story.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
