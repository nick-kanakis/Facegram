package gr.personal.user.util;

import gr.personal.user.domain.Gender;
import gr.personal.user.domain.User;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;
import io.codearte.jfairy.producer.person.Person;

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
                .followers(randomIds())
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
}
