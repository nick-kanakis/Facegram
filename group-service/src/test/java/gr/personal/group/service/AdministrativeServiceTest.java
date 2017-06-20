package gr.personal.group.service;

import gr.personal.group.domain.Group;
import gr.personal.group.domain.GroupRequest;
import gr.personal.group.repository.GroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by nkanakis on 6/19/2017.
 */
@RunWith(SpringRunner.class)
public class AdministrativeServiceTest {

    @Autowired
    private AdministrativeService administrativeService;

    @TestConfiguration
    static class AdministrativeTestContextConfiguration{

        @Bean
        public AdministrativeService administrativeService(){
            return new AdministrativeServiceImpl();
        }

        @Bean
        public CacheManager cacheManager(){
            return new ConcurrentMapCacheManager("testCache");
        }
    }

    @MockBean
    private GroupRepository groupRepository;

    private Group group;
    private GroupRequest groupRequest;

    @Before
    public void setUp() throws Exception {
        group = new Group("testUsername", "testName", "testAbout", null);
        groupRequest = new GroupRequest(null, "testName", "testAbout");
        when(groupRepository.findOne(anyString())).thenReturn(group);
    }

    @Test
    public void shouldFollow() throws Exception{
        String result = administrativeService.followGroup("testGroupId");
        assertEquals(result, "OK");

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailedToFollow() throws Exception{
        administrativeService.followGroup("");
    }

    @Test
    public void shouldUnfollow() throws Exception{
        String result = administrativeService.followGroup("testGroupId");
        assertEquals(result, "OK");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUnfollow() throws Exception{
        administrativeService.followGroup("");
    }

    @Test
    public void shouldCreateGroup() throws Exception{
        String result = administrativeService.createGroup("testUserName", groupRequest);
        assertEquals(result, "OK");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreateGroup() throws Exception{
        administrativeService.createGroup("", groupRequest);
    }

    @Test
    public void shouldRetrieveGroup() throws Exception{
        Group group = administrativeService.retrieveGroup("testGroupId");
        assertEquals(group.getId(), this.group.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveGroup() throws Exception{
        administrativeService.retrieveGroup("");
    }

    @Test
    public void shouldDeleteGroup() throws Exception{
        String result = administrativeService.deleteGroup("testGroupId", "testUsername");
        assertEquals(result, "OK");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToDeleteGroup() throws Exception{
        administrativeService.deleteGroup("", "");
    }

    @Test(expected = UnauthorizedUserException.class)
    public void shouldFailToDeleteGroupUnauthrizedUser() throws Exception{
        administrativeService.deleteGroup("testGroupId", "testUsername2");
    }

    @Test
    public void shouldUpdateGroup() throws Exception{
        administrativeService.updateGroup("testGroupId", groupRequest ,"testUsername");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUpdateGroup() throws Exception{
        administrativeService.updateGroup("",groupRequest,"");
    }

    @Test(expected = UnauthorizedUserException.class)
    public void shouldFailToUpdateGroupUnauthrizedUser() throws Exception{
        administrativeService.updateGroup("testGroupId",groupRequest,"testUsername2");
    }
}
