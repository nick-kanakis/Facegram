package gr.personal.user.helper;

import gr.personal.user.domain.Gender;
import gr.personal.user.domain.Geolocation;
import gr.personal.user.domain.Story;
import gr.personal.user.domain.User;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;
import io.codearte.jfairy.producer.person.Person;
import io.codearte.jfairy.producer.text.TextProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 13/5/2017.
 */

public class FakeDataGenerator {


    public static User generateUser(){

        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        BaseProducer baseProducer = fairy.baseProducer();
        User user = new User.Builder()
                .gender(Gender.MALE)
                .username(String.valueOf(baseProducer.randomBetween(1, 999999999)))
                .name(person.getFirstName())
                .surname(person.getLastName())
                .build();

        return user;
    }

    private static List<String> randomIds(){
        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();

        ArrayList<String> tempList = new ArrayList<>();
        tempList.add(String.valueOf(baseProducer.randomBetween(1, 999999999)));
        tempList.add(String.valueOf(baseProducer.randomBetween(1, 999999999)));
        return tempList;
    }

    public static List<User> generateUsers(){
        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();

        List<User> users = new ArrayList<>();
        for(int i=0; i<=baseProducer.randomBetween(2, 10); i++){
            users.add(generateUser());
        }
        return users;
    }

    public static Story generateStory(){

        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        BaseProducer baseProducer = fairy.baseProducer();

        Story story = new Story.Builder<>()
                .title(textProducer.sentence())
                .userId(String.valueOf(baseProducer.randomBetween(0, 999999999)))
                .geolocation(getRandomGeoLocation())
                .story(textProducer.paragraph())
                .build();

        return story;
    }


    public static Geolocation getRandomGeoLocation(){

        Fairy fairy = Fairy.create();
        BaseProducer baseProducer = fairy.baseProducer();

        return new Geolocation(baseProducer.randomBetween(-90.0, 90.0),baseProducer.randomBetween(-180.0, 180.0));
    }
}
