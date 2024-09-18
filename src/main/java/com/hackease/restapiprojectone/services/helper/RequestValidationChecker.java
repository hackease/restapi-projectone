package com.hackease.restapiprojectone.services.helper;

import com.hackease.restapiprojectone.Exceptions.ValidationException;
import com.hackease.restapiprojectone.utility.Constants;
import org.springframework.stereotype.Component;

@Component
public class RequestValidationChecker {
    
    public void validationCheck(String data) throws ValidationException {
        if (!(data.matches("[a-zA-Z\\s\\-]+")))
            throw new ValidationException(Constants.INVALID_DATA_ENTERED);
    }
    
    public void validationCheck(Integer data) throws ValidationException {
        String dataToString = data.toString();
        if (!(dataToString.matches("^\\d+$")))
            throw new ValidationException(Constants.INVALID_DATA_ENTERED);
    }
    
}
