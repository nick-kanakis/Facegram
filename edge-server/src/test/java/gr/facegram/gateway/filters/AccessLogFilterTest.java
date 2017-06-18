package gr.facegram.gateway.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Nick Kanakis on 14/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessLogFilterTest {

    private AccessLogFilter filter;

    @Before
    public void init(){
        this.filter = new AccessLogFilter();

    }

    @Test
    public void basicProperties() throws Exception{
        Assert.assertEquals(0, this.filter.filterOrder());
        Assert.assertEquals(true, this.filter.shouldFilter());
        Assert.assertEquals("post", this.filter.filterType());
    }

}
