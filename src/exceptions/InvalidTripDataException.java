// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Checked exception thrown when a Trip's attribute values
// violate business rules (base price < $100, duration out of
// 1-20 day range, or client ID not found in current array).

package exceptions;

public class InvalidTripDataException extends Exception {

    public InvalidTripDataException(String message) {
        super(message);
    }

    public InvalidTripDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
