// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Checked exception thrown when a Transportation subclass
// (negative luggage allowance,
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
