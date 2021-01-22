package com.stormpath.validation

import com.google.inject.ImplementedBy

/**
 * Created by IntelliJ IDEA.
 * User: npp
 * Date: 4/8/12
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Any new class that implements this interface can be injected to be used into the production system.
 */
// This sets the default implementor when this service is called.
@ImplementedBy(com.stormpath.validation.DefaultValidationService.class)
public interface ValidationService {
     // Method that must be implemented
    public validateString(String password) throws ValidationException;
}