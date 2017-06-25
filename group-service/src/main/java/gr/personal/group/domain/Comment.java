package gr.personal.group.domain;

import java.time.LocalDateTime;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */
public class Comment extends AbstractEntity {

    private String header;
    private String description;
    private String storyId;

    public String getHeader() {
        return header;
    }
    public String getDescription() {
        return description;
    }
    public String getStoryId() {
        return storyId;
    }

    public class Builder{
        private String id;
        private String userId;
        private String storyId;
        private String header;
        private String description;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder storyId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public Builder header(String header) {
            this.header = header;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        private Comment build(){
            return new Comment(this);
        }
    }

    private Comment(Builder builder){
        this.description = builder.description;
        this.header = builder.header;
        this.storyId = builder.storyId;
        setId(builder.id);
        setUserId(builder.userId);
        setPostDate(LocalDateTime.now());
    }

    //Will be used only to avoid returning null object in case of exception
    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "username='" + getId() + '\'' +
                ", header='" + header + '\'' +
                ", userId='" + getUserId() + '\'' +
                ", storyId='" + getStoryId() + '\'' +
                ", description='" + description + '\'' +
                ", postDate=" + getPostDate() +
                '}';
    }
}
