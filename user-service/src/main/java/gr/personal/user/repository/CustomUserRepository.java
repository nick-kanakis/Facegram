package gr.personal.user.repository;

import gr.personal.user.domain.User;

/**
 * Created by nkanakis on 6/16/2017.
 */
public interface CustomUserRepository {

    User getGroupIdsByUsername(String username);
}
