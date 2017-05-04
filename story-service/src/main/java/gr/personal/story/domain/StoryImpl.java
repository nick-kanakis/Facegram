package gr.personal.story.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */
public class StoryImpl implements Story{

    private String title;
    private String userId;
    private Date postDate;
    private long likes;
    private long unlikes;
    private List<Comment> comments;
    private Geolocation geoLocation;

    static class Builder<T extends Builder>{
        private String title;
        private String userId;
        private Date postDate;
        private long likes;
        private long unlikes;
        private List<Comment> comments;
        private Geolocation geoLocation;

        public Builder geoLocation(Geolocation geoLocation) {
            this.geoLocation = geoLocation;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder postDate(Date postDate) {
            this.postDate = postDate;
            return this;
        }

        public Builder likes(long likes) {
            this.likes = likes;
            return this;
        }

        public Builder unlikes(long unlikes) {
            this.unlikes = unlikes;
            return this;
        }

        public Builder comments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public StoryImpl build(){
            return new StoryImpl(this);
        }
    }

    protected StoryImpl(Builder builder){
        this.title = builder.title;
        this.userId = builder.userId;
        this.postDate = builder.postDate;
        this.likes = builder.likes;
        this.unlikes = builder.unlikes;
        this.comments = builder.comments;
        this.geoLocation = builder.geoLocation;
    }

    public Geolocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(Geolocation geoLocation) {
        this.geoLocation = geoLocation;
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

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getUnlikes() {
        return unlikes;
    }

    public void setUnlikes(long unlikes) {
        this.unlikes = unlikes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "StoryImpl{" +
                "title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                ", postDate=" + postDate +
                ", likes=" + likes +
                ", unlikes=" + unlikes +
                ", comments=" + comments +
                ", geoLocation=" + geoLocation +
                '}';
    }
}
