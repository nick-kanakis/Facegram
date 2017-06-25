package gr.personal.group.helper;

import gr.personal.group.domain.Geolocation;
import gr.personal.group.domain.Story;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;
import io.codearte.jfairy.producer.text.TextProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 13/5/2017.
 */
public class FakeDataGenerator {

    public static List<Story> generateStories() {
        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();
        List<Story> stories = new ArrayList<>();
        for (int i = 0; i <= baseProducer.randomBetween(1, 50); i++) {
            stories.add(generateStory());
        }
        return stories;
    }

    private static List<String> randomIds() {
        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();
        ArrayList<String> tempList = new ArrayList<>();
        tempList.add(String.valueOf(baseProducer.randomBetween(1, 999999999)));
        tempList.add(String.valueOf(baseProducer.randomBetween(1, 999999999)));
        return tempList;
    }

    public static Story generateStory() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        BaseProducer baseProducer = fairy.baseProducer();
        Story story = new Story.Builder<>()
                .title(textProducer.sentence())
                .userId(String.valueOf(baseProducer.randomBetween(0, 999999999)))
                .geolocation(generateRandomGeolocation())
                .id(String.valueOf(baseProducer.randomBetween(0, 999999999)))
                .story(textProducer.paragraph())
                .build();
        return story;
    }

    public static Geolocation generateRandomGeolocation() {
        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();
        return new Geolocation(baseProducer.randomBetween(-90.0, 90.0), baseProducer.randomBetween(-180.0, 180.0));
    }
}
