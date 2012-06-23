package com.brigitte.lookitup.jpa.controllers.exceptions;

/**
 *
 * @author mbohm
 */
public class PreexistingEntityException extends Exception {
    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreexistingEntityException(String message) {
        super(message);
    }
}
