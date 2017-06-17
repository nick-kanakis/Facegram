package gr.personal.user.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nkanakis on 6/16/2017.
 */

@FeignClient(value = "group-service")
@RequestMapping("/group-service")
public interface GroupClient {
    //todo: add correct endpoints! (add prefix in all microservices
    @RequestMapping(method = RequestMethod.POST, value = "/administrative/follow/{groupId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String follow(String groupId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/administrative/unFollow/{groupId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void unFollow(String groupId);
}
