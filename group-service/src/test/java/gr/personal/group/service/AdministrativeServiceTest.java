package gr.personal.group.service;

import gr.personal.group.domain.Group;
import gr.personal.group.domain.GroupRequest;
import gr.personal.group.repository.GroupRepository;
import gr.personal.group.util.Constants;
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

import java.util.Arrays;
import java.util.List;

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
    @MockBean
    private GroupRepository groupRepository;
    private Group group;
    private GroupRequest groupRequest;

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

    @Before
    public void setUp() throws Exception {
        group = new Group("testUsername", "testGroupName", "testAbout", null);
        groupRequest = new GroupRequest(null, "testGroupName", "testAbout");
        when(groupRepository.findOne(anyString())).thenReturn(group);
        when(groupRepository.exists("testGroupName")).thenReturn(true);
        when(groupRepository.findByModerator("testUsername")).thenReturn(Arrays.asList(group));
    }

    @Test
    public void shouldFollow() throws Exception{
        String result = administrativeService.followGroup("testGroupName");
        assertEquals(Constants.OK, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailedToFollow() throws Exception{
        administrativeService.followGroup("");
    }

    @Test
    public void shouldUnfollow() throws Exception{
        String result = administrativeService.followGroup("testGroupName");
        assertEquals(Constants.OK, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUnfollow() throws Exception{
        administrativeService.followGroup("");
    }

    @Test
    public void shouldCreateGroup() throws Exception{
        String result = administrativeService.createGroup("testUserName", groupRequest);
        assertEquals(Constants.OK, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreateGroup() throws Exception{
        administrativeService.createGroup("", groupRequest);
    }

    @Test
    public void shouldRetrieveGroup() throws Exception{
        Group group = administrativeService.retrieveGroup("testGroupName");
        assertEquals(group.getId(), this.group.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveGroup() throws Exception{
        administrativeService.retrieveGroup("");
    }

    @Test
    public void shouldRetrieveMyGroups() throws Exception{
        List<Group> groups = administrativeService.retrieveMyGroups("testUsername");
        assertEquals(groups.get(0).getId(), this.group.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToRetrieveMyGroups() throws Exception{
        administrativeService.retrieveMyGroups("");
    }

    @Test
    public void shouldDeleteGroup() throws Exception{
        String result = administrativeService.deleteGroup("testGroupName", "testUsername");
        assertEquals(Constants.OK, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToDeleteGroup() throws Exception{
        administrativeService.deleteGroup("", "");
    }

    @Test(expected = UnauthorizedUserException.class)
    public void shouldFailToDeleteGroupUnauthrizedUser() throws Exception{
        administrativeService.deleteGroup("testGroupName", "testUsername2");
    }

    @Test
    public void shouldUpdateGroup() throws Exception{
        administrativeService.updateGroup("testGroupName", groupRequest ,"testUsername");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUpdateGroup() throws Exception{
        administrativeService.updateGroup("",groupRequest,"");
    }

    @Test(expected = UnauthorizedUserException.class)
    public void shouldFailToUpdateGroupUnauthrizedUser() throws Exception{
        administrativeService.updateGroup("testGroupName",groupRequest,"testUsername2");
    }
}
