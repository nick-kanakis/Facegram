package gr.personal.group.repository;

import gr.personal.group.domain.Group;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Nick Kanakis on 28/6/2017.
 */

@RunWith(SpringRunner.class)
@DataMongoTest
@ActiveProfiles("repository")
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;
    private Group group;

    @Before
    public void setup(){
        group = new Group("testModerator", "testName", "testAbout", null);

        groupRepository.save(group);
        Assert.assertNotNull(group.getModerator());
    }

    @TestConfiguration
    static class UserRepositoryTestContextConfiguration{
        @Bean
        public ResourceServerProperties resourceServerProperties(){
            return new ResourceServerProperties();
        }
    }

    @After
    public void tearDown(){
        groupRepository.delete(group.getId());
    }

    @Test
    public void shouldFetchMyGroups() throws Throwable{
        List<Group> groups= groupRepository.findByModerator(group.getModerator());
        Assert.assertNotNull(groups);
        Assert.assertEquals(group.getModerator(), groups.get(0).getModerator());
    }

}
