package com.stormpath.validation

/**
 * Created by IntelliJ IDEA.
 * User: npp
 * Date: 4/16/12
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
class ValidationException extends Exception {

    String message = "";

    public ValidationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
