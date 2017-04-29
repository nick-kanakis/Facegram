package gr.facegram.registry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * Created by Nick Kanakis on 29/4/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApplicationTests {

    @Value("${server.port}")
    private int port = 0;

    @Test
    public void catalogLoads() {
        ResponseEntity<Map> entity = new TestRestTemplate("user", "passw0rd!").getForEntity("http://localhost:" + port + "/eureka/apps", Map.class);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

}
