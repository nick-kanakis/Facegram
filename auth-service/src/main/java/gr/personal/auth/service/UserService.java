package gr.personal.auth.service;

import gr.personal.auth.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Nick Kanakis on 3/6/2017.
 */
public interface UserService {


    void create(User user);

}
