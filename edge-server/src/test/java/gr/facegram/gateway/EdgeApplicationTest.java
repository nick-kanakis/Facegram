package gr.facegram.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EdgeApplicationTest {

    @LocalServerPort
    int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void ignoredPatternMissing() {
        ResponseEntity<String> result = this.restTemplate.getForEntity("http://localhost:"+this.port +"/shouldNotForward", String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
