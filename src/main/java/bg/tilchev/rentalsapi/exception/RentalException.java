package bg.tilchev.rentalsapi.exception;

public class RentalException extends RuntimeException {

    public RentalException() {
        super("No rentals found for given criteria");
    }

    public RentalException(String message) {
        super(message);
    }
}
