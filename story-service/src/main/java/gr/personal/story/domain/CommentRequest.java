package gr.personal.story.domain;

import org.hibernate.validator.constraints.NotBlank;


/**
 * Created by Nick Kanakis on 4/5/2017.
 */

public class CommentRequest {

    private String header;
    private String description;

    public String getHeader() {
        return header;
    }


    public String getDescription() {
        return description;
    }

    public CommentRequest(String header, String description) {
        this.header = header;
        this.description = description;
    }

    public CommentRequest() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                " header='" + header + '\'' +
                ", description='" + description  +
                '}';
    }
}
