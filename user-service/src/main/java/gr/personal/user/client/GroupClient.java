package gr.personal.user.client;

import gr.personal.user.domain.GenericJson;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nkanakis on 6/16/2017.
 */

@FeignClient(value = "group-service")
@RequestMapping("/group-service")
public interface GroupClient {

    @RequestMapping(method = RequestMethod.POST, value = "/administrative/follow/{groupId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String follow(@PathVariable("groupId") String groupId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/administrative/unfollow/{groupId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String unFollow(@PathVariable("groupId") String groupId);
}
