package com.adm.exceptions;

/**
 * Created by Milan_Verheij on 26-08-16.
 *
 * Error for when not logged in and trying to order etc.
 */
public class NotLoggedInException extends Exception {

    public NotLoggedInException() {}

    public NotLoggedInException(String message) {
        super(message);
    }
}
