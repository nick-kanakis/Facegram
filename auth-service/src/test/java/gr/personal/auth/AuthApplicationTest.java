package gr.personal.auth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 4/6/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthApplicationTest {

    @Autowired
    private EurekaDiscoveryClient client;

    @Test
    public void contextLoads() throws Exception{
        Assert.assertNotNull(this.client);
    }
}
