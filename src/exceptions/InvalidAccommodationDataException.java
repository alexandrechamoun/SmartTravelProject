// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Checked exception thrown when an Accommodation subclass
// attribute violates business rules (price <= $0, hotel star
// rating not 1-5, hostel price exceeding $150/night, etc.)

package exceptions;

public class InvalidAccommodationDataException extends Exception {

    public InvalidAccommodationDataException(String message) {
        super(message);
    }

    public InvalidAccommodationDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
