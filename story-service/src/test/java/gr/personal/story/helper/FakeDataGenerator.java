package gr.personal.story.helper;

import gr.personal.story.domain.*;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;
import io.codearte.jfairy.producer.DateProducer;
import io.codearte.jfairy.producer.text.TextProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 5/5/2017.
 */
public class FakeDataGenerator {

    public static List<Story> generateStories(){
        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();

        List<Story> stories = new ArrayList<>();
        for(int i=0; i<=baseProducer.randomBetween(1, 50); i++){
            stories.add(generateStory());
        }
        return stories;
    }

    public static Story generateStory(){

        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        BaseProducer baseProducer = fairy.baseProducer();

        Story story = new Story.Builder<>()
                .title(textProducer.sentence())
                .userId(String.valueOf(baseProducer.randomBetween(0, 999999999)))
                .geolocation(getRandomGeoLocation())
                .groupId(null)
                .id(String.valueOf(baseProducer.randomBetween(0, 999999999)))
                .story(textProducer.paragraph())
                .build();

        return story;
    }


    public static Story generateStoryWithoutId(){

        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        BaseProducer baseProducer = fairy.baseProducer();

        Story story = new Story.Builder<>()
                .title(textProducer.sentence())
                .userId(String.valueOf(baseProducer.randomBetween(0, 999999999)))
                .geolocation(getRandomGeoLocation())
                .groupId(null)
                .story(textProducer.paragraph())
                .build();

        return story;
    }

    public static List<Comment> generateComments() {

        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();

        List<Comment> comments = new ArrayList<>();
        for(int i=0; i<=baseProducer.randomBetween(2, 10); i++){
            comments.add(generateComment());
        }
        return comments;
    }

    public static Comment generateComment() {

        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        BaseProducer baseProducer = fairy.baseProducer();
        DateProducer dateProducer = fairy.dateProducer();

        Comment comment = new Comment.Builder()
                .storyId(String.valueOf(baseProducer.randomBetween(0, 999999999)))
                .description(textProducer.paragraph(10))
                .userId(String.valueOf(baseProducer.randomBetween(0, 999999999)))
                .header(textProducer.sentence())
                .id(String.valueOf(baseProducer.randomBetween(0, 999999999)))
                .build();

        return comment;
    }

    public static Geolocation getRandomGeoLocation(){

        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();

        return new Geolocation(baseProducer.randomBetween(-90.0, 90.0),baseProducer.randomBetween(-180.0, 180.0));
    }


    public static StoryRequest generateStoryRequest() {

        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        BaseProducer baseProducer = fairy.baseProducer();

        return new StoryRequest(textProducer.paragraph(),textProducer.sentence(),null, getRandomGeoLocation());

    }

    public static CommentRequest generateCommentRequest(){
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        BaseProducer baseProducer = fairy.baseProducer();

       return new CommentRequest(textProducer.sentence(),textProducer.sentence());
    }
}
