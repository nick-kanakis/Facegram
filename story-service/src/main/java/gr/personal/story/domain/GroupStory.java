package gr.personal.story.domain;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */
public class GroupStory extends StoryImpl {

    private String ownerGroupId;

    public String getOwnerGroupId() {
        return ownerGroupId;
    }

    public void setOwnerGroupId(String ownerGroupId) {
        this.ownerGroupId = ownerGroupId;
    }

    public static class Builder extends StoryImpl.Builder<Builder>{
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
        ownerGroupId = builder.ownerGroupId;
    }
}
