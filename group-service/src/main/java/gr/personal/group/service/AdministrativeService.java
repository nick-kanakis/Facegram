package gr.personal.group.service;

import gr.personal.group.domain.Group;
import gr.personal.group.domain.GroupRequest;

import java.util.List;

/**
 * Created by Nick Kanakis on 17/6/2017.
 */
public interface AdministrativeService {
    String followGroup(String groupId);

    String unfollowGroup(String groupId);

    String createGroup(String name, GroupRequest groupRequest);

    Group retrieveGroup(String groupId);

    String deleteGroup(String groupId, String username);

    String updateGroup(String groupId, GroupRequest groupRequest, String username);

    List<Group> retrieveMyGroups(String username);
}
