package bg.tilchev.rentalsapi.exception;

import bg.tilchev.rentalsapi.entity.Rental;

/**
 * Created on 2022-01-17.
 */
public class RentalException extends RuntimeException {

    public RentalException() {
        super("No rentals found for given criteria");
    }
}
