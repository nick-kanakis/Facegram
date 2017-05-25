package gr.personal.story.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */

public class CommentRequest {

    private String header;
    private String userId;
    private String description;

    public String getHeader() {
        return header;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public CommentRequest(String header, String userId, String description) {
        this.header = header;
        this.userId = userId;
        this.description = description;
    }

    public CommentRequest() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                " header='" + header + '\'' +
                ", userId='" + userId + '\'' +
                ", description='" + description  +
                '}';
    }
}
