package gr.personal.auth.service;

import gr.personal.auth.domain.User;
import gr.personal.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by Nick Kanakis on 3/6/2017.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository repository;


    @Override
    public void create(User user) {

        User existing = repository.findByUsername(user.getUsername());
        Assert.isNull(existing, "User already exists: " + user.getUsername());

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        repository.save(user);

        log.info("New user has been created: {}", user.getUsername());
    }

    @Override
    public void delete(String username) {

        repository.delete(username);

        log.info("New user has been deleted: {}", username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
