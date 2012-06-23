package com.brigitte.lookitup.jpa.controllers.exceptions;

/**
 *
 * @author mbohm
 */
public class NonexistentEntityException extends Exception {
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityException(String message) {
        super(message);
    }
}
