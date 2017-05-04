package gr.personal.story;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */

@SpringBootApplication
@EnableDiscoveryClient
public class StoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoryApplication.class, args);
    }
}
