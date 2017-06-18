package gr.personal.group.domain.validator;

import gr.personal.group.domain.GroupRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Nick Kanakis on 17/6/2017.
 */
public class GroupRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return GroupRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        GroupRequest groupRequest = (GroupRequest) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","name.empty");

    }
}
