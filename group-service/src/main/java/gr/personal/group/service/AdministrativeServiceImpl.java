package gr.personal.group.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import gr.personal.group.domain.Group;
import gr.personal.group.domain.GroupRequest;
import gr.personal.group.repository.GroupRepository;
import gr.personal.group.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Kanakis on 17/6/2017.
 */
@Service
public class AdministrativeServiceImpl implements AdministrativeService {

    private static final Logger logger = LoggerFactory.getLogger(AdministrativeServiceImpl.class);
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CacheManager cacheManager;

    @Override
    @HystrixCommand(fallbackMethod = "followGroupFallback", ignoreExceptions = IllegalArgumentException.class)
    public String followGroup(String groupId) {
        Assert.hasLength(groupId, "followGroup input is empty");
        Group group = groupRepository.findOne(groupId);
        if(group == null){
            logger.warn("No group with id={} was found.", groupId);
            return Constants.NOK;
        }
        group.addFollower();
        groupRepository.save(group);
        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "unfollowGroupFallback", ignoreExceptions = IllegalArgumentException.class)
    public String unfollowGroup(String groupId) {
        Assert.hasLength(groupId, "followGroup input is empty");
        Group group = groupRepository.findOne(groupId);
        if(group == null){
            logger.warn("No group with id={} was found.", groupId);
            return Constants.NOK;
        }
        group.removeFollower();
        groupRepository.save(group);
        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "createGroupFallback", ignoreExceptions = IllegalArgumentException.class)
    public String createGroup(String username, GroupRequest groupRequest) {
        Assert.hasLength(username, "createGroup input is empty");
        Group group = new Group(username, groupRequest.getName(), groupRequest.getAbout(), groupRequest.getRelatedGroupIds());
        groupRepository.save(group);
        return Constants.OK;
    }

    @Override
    @CachePut(cacheNames = "RetrieveGroup", key = "#groupId")
    @HystrixCommand(fallbackMethod = "retrieveGroupFallback", ignoreExceptions = IllegalArgumentException.class)
    public Group retrieveGroup(String groupId) {
        Assert.hasLength(groupId, "retrieveGroup input is empty");
        Group group = groupRepository.findOne(groupId);
        if(group == null){
            logger.warn("No group with id={} was found.", groupId);
            return new Group();
        }
        return group;
    }

    @Override
    @CachePut(cacheNames = "RetrieveMyGroups", key = "#username")
    @HystrixCommand(fallbackMethod = "retrieveMyGroupsFallback", ignoreExceptions = IllegalArgumentException.class)
    public List<Group> retrieveMyGroups(String username) {
        Assert.hasLength(username, "retrieveMyGroups input is empty");
        List<Group> groups = groupRepository.findByModerator(username);
        if(groups == null){
            logger.warn("No group for username={} was found.", username);
            return new ArrayList<>();
        }
        return groups;
    }

    @Override
    @HystrixCommand(fallbackMethod = "deleteGroupFallback", ignoreExceptions = IllegalArgumentException.class)
    public String deleteGroup(String groupId, String username) {
        Assert.hasLength(groupId, "followGroup input is empty");

        checkUser(username, groupId);
        if(!groupRepository.exists(groupId)){
            logger.warn("No group with id={} was found.", groupId);
            return Constants.NOK;
        }
        groupRepository.delete(groupId);
        return Constants.OK;
    }

    @Override
    @HystrixCommand(fallbackMethod = "updateGroupFallback", ignoreExceptions = IllegalArgumentException.class)
    public String updateGroup(String groupId, GroupRequest groupRequest, String username) {
        Assert.hasLength(groupId, "followGroup input is empty");
        Assert.hasLength(username, "followGroup input is empty");

        checkUser(username, groupId);
        Group group = groupRepository.findOne(groupId);
        group.setAbout(groupRequest.getAbout());
        group.setName(groupRequest.getName());
        group.setRelatedGroupIds(groupRequest.getRelatedGroupIds());

        groupRepository.save(group);
        return Constants.OK;

    }


    private boolean checkUser(String username, String groupId) throws UnauthorizedUserException {
        Group group = groupRepository.findOne(groupId);
        if(group==null )
            return false;
        if(!username.equals(group.getModerator()))
            throw new UnauthorizedUserException("Unauthorized user for this action.");
        return true;
    }


    /**
     * Hystrix fallback methods
     * */

    public String followGroupFallback(String groupId, Throwable t){
        logger.error("Follow group fallback for groupId: " + groupId, t);
        return Constants.NOK;
    }

    public String unfollowGroupFallback(String groupId, Throwable t){
        logger.error("Unfollow group fallback for groupId: " + groupId, t);
        return Constants.NOK;
    }

    public String createGroupFallback(String username, GroupRequest groupRequest, Throwable t){
        logger.error("Create group fallback, group Name : " + groupRequest.getName(), t);
        return Constants.NOK;
    }

    public Group retrieveGroupFallback(String groupId, Throwable t) {
        logger.error("Retrieve group fallback for groupId: " + groupId + ". Returning Group from Cache", t);
        if (cacheManager.getCache("RetrieveGroup") != null && cacheManager.getCache("RetrieveGroup").get(groupId) != null) {
            return cacheManager.getCache("RetrieveGroup").get(groupId, Group.class);
        } else {
            logger.error("Retrieve group Fallback for groupId: " + groupId + ". Cache is empty.");
            return new Group();
        }
    }

    public List<Group> retrieveMyGroupsFallback(String username, Throwable t) {
        logger.error("Retrieve my groups fallback for username: " + username + ". Returning Groups from Cache", t);
        if (cacheManager.getCache("RetrieveMyGroups") != null && cacheManager.getCache("RetrieveMyGroups").get(username) != null) {
            return cacheManager.getCache("RetrieveMyGroups").get(username, ArrayList.class);
        } else {
            logger.error("Retrieve my groups Fallback for username: " + username + ". Cache is empty.");
            return new ArrayList<>();
        }
    }

    public String deleteGroupFallback(String groupId, String username, Throwable t) {
        logger.error("Delete group fallback for groupId: " + groupId, t);
        return Constants.NOK;
    }

    public String updateGroupFallback(String groupId, GroupRequest groupRequest, String username, Throwable t) {
        logger.error("Update group fallback for groupId: " + groupId, t);
        return Constants.NOK;
    }
}
