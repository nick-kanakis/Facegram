package gr.personal.story.domain.validator;

import gr.personal.story.domain.StoryRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by nkanakis on 5/26/2017.
 */
@Component("beforeCreateStoryRequestValidator")
public class StoryRequestValidator implements Validator {

    private GeolocationValidator geolocationValidator;

    @Override
    public boolean supports(Class<?> aClass) {
        return StoryRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"title","title.empty");

        StoryRequest storyRequest = (StoryRequest) o;
        geolocationValidator = new GeolocationValidator();

        ValidationUtils.invokeValidator(this.geolocationValidator, storyRequest.getGeolocation(), errors);

    }
}
