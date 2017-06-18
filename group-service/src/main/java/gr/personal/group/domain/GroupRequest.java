package gr.personal.group.domain;

import java.util.List;

/**
 * Created by Nick Kanakis on 17/6/2017.
 */
public class GroupRequest {

    private List<String> relatedGroupIds;
    private String name;
    private String about;

    public GroupRequest() {
    }

    public GroupRequest( List<String> relatedGroupIds, String name, String about) {
        this.relatedGroupIds = relatedGroupIds;
        this.name = name;
        this.about = about;
    }

    public List<String> getRelatedGroupIds() {
        return relatedGroupIds;
    }

    public void setRelatedGroupIds(List<String> relatedGroupIds) {
        this.relatedGroupIds = relatedGroupIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
