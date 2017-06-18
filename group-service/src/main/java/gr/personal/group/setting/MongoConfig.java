package gr.personal.group.setting;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by nkanakis on 5/19/2017.
 */
@Configuration
@EnableMongoRepositories(basePackages = "gr.personal.group.repository")
public class MongoConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "Group-Service";
    }

    @Override
    public Mongo mongo() throws Exception {
        //todo retrieve them from yml
        return  new MongoClient("127.0.0.1", 27017);
    }
}
