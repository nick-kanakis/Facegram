package gr.personal.group;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by nkanakis on 6/19/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupApplicationTest {

    @Autowired
    private EurekaDiscoveryClient client;

    @Test
    public void contextLoads() throws Exception{
        Assert.assertNotNull(this.client);
    }
}
