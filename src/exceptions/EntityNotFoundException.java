// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Checked exception thrown when a search by ID (client, trip,
// transportation, or accommodation) finds no matching entity
// in the current in-memory arrays.

package exceptions;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
