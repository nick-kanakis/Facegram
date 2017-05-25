package gr.personal.story.util;

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
        DateProducer dateProducer = fairy.dateProducer();

        Story story = new Story.Builder<>()
                .likes(baseProducer.randomBetween(0, 1000))
                .title(textProducer.sentence())
                .unlikes(baseProducer.randomBetween(0, 500))
                .userId(String.valueOf(baseProducer.randomBetween(0, 999999999)))
                .postDate(dateProducer.randomDateInThePast(10).toDate())
                .geolocation(getRandomGeoLocation())
                .comments(generateComments())
                .id(String.valueOf(baseProducer.randomBetween(0, 999999999)))
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

        Comment comment = new Comment();
        comment.setPostDate(dateProducer.randomDateInThePast(10).toDate());
        comment.setDescription(textProducer.paragraph(10));
        comment.setHeader(textProducer.sentence());
        comment.setId(String.valueOf(baseProducer.randomBetween(0, 999999999)));
        comment.setUserId(String.valueOf(baseProducer.randomBetween(0, 999999999)));

        return comment;
    }

    public static Geolocation getRandomGeoLocation(){

        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();

        return new Geolocation(baseProducer.randomBetween(-90.0, 90.0),baseProducer.randomBetween(-180.0, 180.0));
    }



}
