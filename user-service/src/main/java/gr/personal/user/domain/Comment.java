package gr.personal.user.domain;

import java.util.Date;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */
public class Comment {

    private String id;
    private String header;
    private String userId;
    private String description;
    private Date postDate;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", header='" + header + '\'' +
                ", userId='" + userId + '\'' +
                ", description='" + description + '\'' +
                ", postDate=" + postDate +
                '}';
    }
}
