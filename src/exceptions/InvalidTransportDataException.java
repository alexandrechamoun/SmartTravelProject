// -------------------------------------------------------------
// Assignment 2
// Written by: Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Checked exception thrown when a Transportation subclass
// attribute violates business rules (negative luggage allowance,
// bus with fewer than 1 stop, invalid fare values, etc.)

package exceptions;

public class InvalidTransportDataException extends Exception {

    public InvalidTransportDataException(String message) {
        super(message);
    }

    public InvalidTransportDataException(String message, Throwable cause) {
        super(message, cause);
    }
}