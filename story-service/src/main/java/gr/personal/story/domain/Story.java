package gr.personal.story.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@Document(collection = "stories")
public class Story extends AbstractEntity{

    private String story;
    private String title;
    private long likes;
    private long unlikes;
    private String groupId;
    private List<Comment> comments;
    private Geolocation geolocation;


    public static class Builder<T extends Builder>{
        private String title;
        private String id;
        private String userId;
        private Geolocation geolocation;
        private String story;
        private String groupId;


        public Builder geolocation(Geolocation geoLocation) {
            this.geolocation = geoLocation;
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
        setPostDate(new Date());
        this.likes = 0;
        this.unlikes = 0;
        this.comments = new ArrayList<>();
        this.geolocation = builder.geolocation;
        setId(builder.id);
        this.story = builder.story;
        this.groupId = builder.groupId;
    }

    public Story() {
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public long getLikes() {
        return likes;
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

    public long getUnlikes() {
        return unlikes;
    }

    public List<Comment> getComments() {
        return comments;
    }


    public void like() {
        this.likes++;
    }

    public void unlike() {
        this.unlikes++;
    }


    public void addComment(Comment comment) {
        this.comments.add(comment);
    }


    /*DB helper methods*/
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
}
