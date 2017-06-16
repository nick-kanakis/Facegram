package gr.personal.user.client;

import gr.personal.user.domain.RegistrationUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by Nick Kanakis on 8/6/2017.
 */

@FeignClient(value="auth-service")
@RequestMapping("/uaa")
public interface AuthClient {

    @RequestMapping(value = "/users/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void createUser(@Valid @RequestBody RegistrationUser user);

    @RequestMapping(value = "/users/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void deleteUser(@Valid @RequestBody String username);
}

