import com.google.inject.Guice
import com.google.inject.Injector
import com.stormpath.validation.ValidationModule
import com.stormpath.validation.ValidationService

    //In current production, this addition could inject a configurable ValidationService defined by the ValidationModule() which can live in a external lib.
    //This allows ValidationService to be highly configurable outside of the caller.
    Injector injector = Guice.createInjector(new ValidationModule());
    com.stormpath.validation.ValidationService validationService = injector.getInstance(ValidationService.class);
    validationService.validateString("panahi3")

