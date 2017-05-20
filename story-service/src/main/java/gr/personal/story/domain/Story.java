package gr.personal.story.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Document(collection = "stories")
public class Story {

    @Id
    private String id;
    private String story;
    private String title;
    private String userId;
    private Date postDate;
    private long likes;
    private long unlikes;
    private List<Comment> comments;
    private Geolocation geolocation;


    public static class Builder<T extends Builder>{
        private String title;
        private String userId;
        private Date postDate;
        private long likes;
        private long unlikes;
        private List<Comment> comments;
        private Geolocation geolocation;
        private String id;
        private String story;


        public Builder geolocation(Geolocation geoLocation) {
            this.geolocation = geoLocation;
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

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder story(String story) {
            this.story = story;
            return this;
        }

        public Story build(){
            return new Story(this);
        }

    }

    protected Story(Builder builder){
        this.title = builder.title;
        this.userId = builder.userId;
        this.postDate = builder.postDate;
        this.likes = builder.likes;
        this.unlikes = builder.unlikes;
        this.comments = builder.comments;
        this.geolocation = builder.geolocation;
        this.id = builder.id;
        this.story = builder.story;
    }

    public Story() {
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
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

    public Comment getCommentById(String commentId) {

        for (Comment comment:comments) {
            if(comment.getId().equals(commentId))
                return comment;
        }
        return new Comment();
    }

    public void deleteCommentById(String commentId) {

        /*
        * The best solution in order to delete an element in an List
        * without O(N^2) (duplicate list). Also with streams references to the list
        * would be not be effected.
        * */

        Iterator<Comment> iterator = comments.iterator();

        while (iterator.hasNext()){
            Comment comment = iterator.next();
            if(comment.getId().equals(commentId))
                iterator.remove();

        }
    }


    @Override
    public String toString() {
        return "Story{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                ", postDate=" + postDate +
                ", likes=" + likes +
                ", unlikes=" + unlikes +
                ", comments=" + comments +
                ", geolocation=" + geolocation +
                '}';
    }
}
