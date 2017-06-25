package gr.personal.user.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by nkanakis on 5/19/2017.
 */
@Configuration
@EnableMongoRepositories(basePackages = "gr.personal.user.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${mongo.host}")
    private String HOST;
    @Value("${mongo.port}")
    private Integer PORT;
    @Value("${mongo.dbname}")
    private String DB_NAME;

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(HOST, PORT);
    }
}
