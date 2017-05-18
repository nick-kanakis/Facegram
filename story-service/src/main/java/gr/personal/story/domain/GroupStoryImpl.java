package gr.personal.story.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */
@Document(collection = "stories")
public class GroupStoryImpl extends StoryImpl {

    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public static class Builder extends StoryImpl.Builder<Builder>{
        private String ownerGroupId;

        public Builder ownerGroupId(String ownerGroupId) {
            this.ownerGroupId = ownerGroupId;
            return this;
        }

        public GroupStoryImpl build(){
            return new GroupStoryImpl(this);
        }
    }

    public GroupStoryImpl(Builder builder) {
        super(builder);
        groupId = builder.ownerGroupId;
    }
}
