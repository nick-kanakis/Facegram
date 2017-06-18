package gr.personal.group.controller;

import gr.personal.group.aop.annotations.LogExecutionTime;
import gr.personal.group.domain.Story;
import gr.personal.group.service.HomepageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Nick Kanakis on 17/6/2017.
 */
@RestController
@RequestMapping("/homepage")
public class HomepageController {

    private Logger logger = LoggerFactory.getLogger(HomepageController.class);

    @Autowired
    private HomepageService homepageService;

    @LogExecutionTime
    @RequestMapping(path = "/hot/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> getHotStories(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getHotStories (groupId={})", groupId);
        List<Story> hotStories = homepageService.getHotStories(groupId);
        logger.debug("Exiting getHotStories (groupId={}, numberOfStories={})", groupId, hotStories.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(hotStories);
    }

    @LogExecutionTime
    @RequestMapping(path = "/new/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> getNewStories(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getNewStories (groupId={})", groupId);
        List<Story> newStories = homepageService.getNewStories(groupId);
        logger.debug("Exiting getNewStories (groupId={}, numberOfStories={} )", groupId, newStories.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(newStories);
    }

    @LogExecutionTime
    @RequestMapping(path = "/top/{groupId}", method = RequestMethod.GET)
    public ResponseEntity<List<Story>> getTopStories(@PathVariable String groupId) throws ParseException {
        logger.debug("Entering getTopStories (groupId={})", groupId);
        List<Story> topStories = homepageService.getTopStories(groupId);
        logger.debug("Exiting getTopStories (groupId={}), numOfStories={}", groupId, topStories.size());
        return  ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(topStories);
    }
}
