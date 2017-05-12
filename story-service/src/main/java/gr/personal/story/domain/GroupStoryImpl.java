package gr.personal.story.domain;

/**
 * Created by Nick Kanakis on 4/5/2017.
 */
public class GroupStoryImpl extends StoryImpl {

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

        public GroupStoryImpl build(){
            return new GroupStoryImpl(this);
        }
    }

    public GroupStoryImpl(Builder builder) {
        super(builder);
        ownerGroupId = builder.ownerGroupId;
    }
}
