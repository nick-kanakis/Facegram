package gr.personal.story.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */
@Document(collection = "stories")
public class GroupStory extends Story {

    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public static class Builder extends Story.Builder<Builder>{
        private String ownerGroupId;

        public Builder ownerGroupId(String ownerGroupId) {
            this.ownerGroupId = ownerGroupId;
            return this;
        }

        public GroupStory build(){
            return new GroupStory(this);
        }
    }

    public GroupStory(Builder builder) {
        super(builder);
        groupId = builder.ownerGroupId;
    }

    public GroupStory() {
        super();
    }
}
