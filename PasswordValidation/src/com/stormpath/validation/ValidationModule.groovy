package com.stormpath.validation

import com.google.inject.AbstractModule;

/**
 * Created by IntelliJ IDEA.
 * User: npp
 * Date: 4/8/12
 * Time: 6:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidationModule extends AbstractModule {
    @Override
    protected void configure() {
        /*
        * This tells Guice that whenever it sees a dependency on a ValidationService,
        * it should satisfy the dependency using a DefaultValidationService.
        * This binding is what allows the configuration code to be injected.
        */
        bind(ValidationService.class).to(DefaultValidationService.class);
        //bind(AnotherService.class).to(AnotherService.class);
    }
}