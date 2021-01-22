package com.stormpath.validation

import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator

/**
 * Created by IntelliJ IDEA.
 * User: npp
 * Date: 4/8/12
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Any class that implements the ValidationService could be used for injection into the production system. Here we define
 * the default validation from the currently known rules. As more rules need to be added, they can be added here and
 * dropped as an external jar in the production system.
 */
class DefaultValidationService implements ValidationService {

    private final Logger log = Logger.getLogger(getClass())

    public DefaultValidationService() {
        File log4jfile = new File("./conf/log4j.properties"); // Configured for JAVA.
        PropertyConfigurator.configure(log4jfile.getAbsolutePath());
    }


    /////////////////////////////////////////PASSWORD RULES////////////////////////////////////////////////


    /**
     * Password should be between 5 and 12 characters in length.
     *
     * @param password
     */
    private hasValidLength(String password) throws ValidationException {
        log.debug "hasValidLength - Testing password $password for length."

        int passwordLength = password.length();
        if (passwordLength > 5 && passwordLength < 12) {
            log.debug "hasValidLength - Password $password has the correct length."
        } else {
            def msg = "Password $password does not have a valid length."
            log.error msg
            throw new ValidationException(msg)
        }
    }


    /**
     * Password must not contain any sequence of characters immediately followed by the same sequence.
     *
     * @param password
     */
    private hasValidSequences(String password) throws ValidationException {
        log.debug "hasValidSequences - Testing password $password for invalid sequences."

        int passwordLength = password.length();
        char[] chars = password.getChars();
        for (int i = 0; i < passwordLength; i++) {
            if (i > 0) {
                if (chars[i-1] == chars[i]) {
                    def msg = "Password $password contains an invalid sequence of characters ${chars[i-1]} followed by ${chars[i]}."
                    log.error msg
                    throw new ValidationException(msg)
                }
            }
        }

        log.debug "hasValidSequences - Password $password has valid sequences of characters."
    }


    /**
     * Password should consist of a mixture of lowercase letters and numerical digits only,
     * with at least one of each.
     *
     * @param password
     */
    private hasValidCharacters(String password) throws ValidationException {
        log.debug "hasValidCharacters - Testing password $password for valid characters."

        boolean hasLetter = false
        boolean hasNumber = false
        int passwordLength = password.length();
        char[] chars = password.getChars();

        for (int i = 0; i < passwordLength; i++) {
            if (chars[i].isDigit()) {
                hasNumber = true
            } else if (chars[i].isLetter()) {
                hasLetter = true
                if (!chars[i].isLowerCase()) {
                    def msg = "Password $password contains a Upper-case letter."
                    log.error msg
                    throw new ValidationException(msg)
                }
            } else {
                def msg = "Password $password contains an invalid character."
                log.error msg
                throw new ValidationException(msg)
            }
        }

        if (!hasLetter || !hasNumber) {
            def msg = "Password $password is missing either a letter or number character. It must contain both."
            log.error msg
            throw new ValidationException(msg)
        }

        log.debug "hasValidCharacters - Password $password has valid characters."
    }


    /////////////////////////////////////////INTERFACE METHOD////////////////////////////////////////////////


    /**
     * Method from interface that must be implemented. This method should consist of all password rules through single
     * method calls. Each method is responsible for one password rule.
     *
     * @param password
     * @return true or false depending on if the password passes validation
     */
    public validateString(String password) throws ValidationException {
        log.debug "validateString - Testing password $password for validation against rules."
        // Place calls to rules here.
        hasValidLength(password)
        hasValidSequences(password)
        hasValidCharacters(password)

        log.info "validateString - Password $password has been validated."
    }

}

