package gr.facegram.gateway;

import gr.facegram.gateway.filters.AccessLogFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.context.annotation.Bean;

/**
 * Created by Nick Kanakis on 1/5/2017.
 */
@SpringBootApplication
@EnableZuulProxy
public class EdgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeApplication.class, args);
    }

    @Bean
    public AccessLogFilter accessLogFilter(){
        return new AccessLogFilter();
    }
}
