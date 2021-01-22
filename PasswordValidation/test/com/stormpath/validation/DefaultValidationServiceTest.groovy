package com.stormpath.validation

import junit.framework.TestCase
/**
 * Created by IntelliJ IDEA.
 * User: npp
 * Date: 4/8/12
 * Time: 8:14 PM
import
 * To change this template use File | Settings | File Templates.
 */
public class DefaultValidationServiceTest extends TestCase {
    
    def validationService
    
    String invalidPassword1 = "NotValid"
    String invalidPassword2 = "nickpan"
    String invalidPassword3 = "no"
    String invalidPassword4 = "thisstring1s2long"
    String invalidPassword5 = "abbarule5"
    String invalidPassword6 = "aardvark1"
    String invalidPassword7 = "bardvark1cc"
    String validPassword = "panahi3"

    public void testValidateString() {

        validationService = new DefaultValidationService();

        
        // Test individual rules with various input

        validationService.hasValidCharacters(invalidPassword6) // Password has valid characters so no exception should be thrown

        try {
            validationService.hasValidCharacters(invalidPassword1)
            fail() // Test should throw an exception because upper-case characters are not allowed
        } catch (ValidationException ve) { 
            println "ValidationException: ${ve.message}"
        }


        validationService.hasValidLength(invalidPassword2) // Password is of valid length so no exception should be thrown

        try {
            validationService.hasValidLength(invalidPassword4)
            fail() // Test should thrown an exception becuase string is longer than allowed length
        } catch (ValidationException ve) {
            println "ValidationException: ${ve.message}"
        }


        validationService.hasValidSequences(invalidPassword1)

        try {
            validationService.hasValidSequences(invalidPassword5)
            fail() // Test should throw an exception because string has consecutive letters
        } catch (ValidationException ve) {
            println "ValidationException: ${ve.message}"
        }


       // Test all the rules with various input

        validationService.validateString(validPassword);

        try {
            validationService.validateString(invalidPassword1);
            fail()
        } catch (ValidationException ve) {
            println "ValidationException: ${ve.message}"
        }

        try {
            validationService.validateString(invalidPassword2);
            fail()
        } catch (ValidationException ve) {
            println "ValidationException: ${ve.message}"
        }

        try {
            validationService.validateString(invalidPassword3);
            fail()
        } catch (ValidationException ve) {
            println "ValidationException: ${ve.message}"
        }

        try {
            validationService.validateString(invalidPassword4);
            fail()
        } catch (ValidationException ve) {
            println "ValidationException: ${ve.message}"
        }

        try {
            validationService.validateString(invalidPassword5);
            fail()
        } catch (ValidationException ve) {
            println "ValidationException: ${ve.message}"
        }

        try {
            validationService.validateString(invalidPassword6);
            fail()
        } catch (ValidationException ve) {
            println "ValidationException: ${ve.message}"
        }

        try {
            validationService.validateString(invalidPassword7);
            fail()
        } catch (ValidationException ve) {
            println "ValidationException: ${ve.message}"
        }
    }
}
