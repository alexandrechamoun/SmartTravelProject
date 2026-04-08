// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Checked exception thrown when a Client's attribute values
// (empty names, invalid email format,
// lengths exceeding allowed limits, etc.)

package exceptions;

public class InvalidClientDataException extends Exception {

    public InvalidClientDataException(String message) {
        super(message);
    }

    public InvalidClientDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
