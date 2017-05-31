package gr.personal.story.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

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

    public static class Builder{
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

        public Comment build(){
           return new Comment(this);
        }
    }

    private Comment(Builder builder) {
        this.header = builder.header;
        this.description = builder.description;
        this.storyId = builder.storyId;
        setUserId(builder.userId);
        setPostDate(new Date());
        setId(builder.id);
    }

    /*Will only be used as returned object to avoid returning null*/
    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + getId() + '\'' +
                ", header='" + header + '\'' +
                ", userId='" + getUserId() + '\'' +
                ", description='" + description + '\'' +
                ", postDate=" + getPostDate() +
                '}';
    }
}
