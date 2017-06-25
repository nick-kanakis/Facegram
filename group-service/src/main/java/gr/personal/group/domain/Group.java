package gr.personal.group.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 17/6/2017.
 */
@Document(collection = "group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    public String name;
    private String moderator;
    private long followers;
    private String about;
    private List<String> relatedGroupIds;

    //only to be used to avoid returning null in case of exception.
    public Group() {
    }

    public Group(String moderator,String name, String about, List<String> relatedGroupIds) {
        this.moderator = moderator;
        this.name = name;
        this.about = about;
        if(relatedGroupIds!=null)
            this.relatedGroupIds = relatedGroupIds;
        else
            this.relatedGroupIds = new ArrayList<>();
        this.followers=0;
    }

    public String getId() {
        return id;
    }

    public String getModerator() {
        return moderator;
    }

    public long getFollowers() {
        return followers;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public List<String> getRelatedGroupIds() {
        return relatedGroupIds;
    }

    public void addFollower() {
        this.followers ++;
    }

    public void removeFollower() {
        this.followers --;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRelatedGroupIds(List<String> relatedGroupIds) {
        this.relatedGroupIds = relatedGroupIds;
    }

    public void addRelatedGroupId(String relatedGroupId) {
        this.relatedGroupIds.add(relatedGroupId);
    }
}
