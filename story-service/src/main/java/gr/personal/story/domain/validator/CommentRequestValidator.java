package gr.personal.story.domain.validator;

import gr.personal.story.domain.CommentRequest;
import gr.personal.story.domain.StoryRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by nkanakis on 5/26/2017.
 */
@Component("beforeCreateCommentRequestValidator")
public class CommentRequestValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return CommentRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        CommentRequest commentRequest = (CommentRequest) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"header","header.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"userId","userId.empty");

    }
}
