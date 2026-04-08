// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Unchecked exception thrown when attempting to add a client
// whose email address already exists in the client array.
// Emails must be unique across all clients in the system.

package exceptions;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
