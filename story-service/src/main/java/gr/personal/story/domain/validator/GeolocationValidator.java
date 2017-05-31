package gr.personal.story.domain.validator;

import gr.personal.story.domain.Geolocation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by nkanakis on 5/26/2017. Custom Validator for Geolocation object
 */

public class GeolocationValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Geolocation.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Geolocation geolocation = (Geolocation) o;

        if(geolocation.getLatitude()> 90.0 || geolocation.getLatitude()< -90.0){
            errors.reject("latitude", "latitude.outOfRange");
        }

        if(geolocation.getLongitude()> 180.0 || geolocation.getLatitude()<-180.0){
            errors.reject("longitude", "longitude.outOfRange");
        }

    }
}
