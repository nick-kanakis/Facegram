package gr.personal.user.domain.validator;

import gr.personal.user.domain.UserRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Nick Kanakis on 27/5/2017.
 */
@Component("beforeCreateUserRequestValidator")
public class UserRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","username.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"gender","gender.empty");
    }
}
