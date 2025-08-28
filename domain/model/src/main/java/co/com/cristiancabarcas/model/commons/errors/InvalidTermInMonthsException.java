package co.com.cristiancabarcas.model.commons.errors;

public class InvalidTermInMonthsException extends RuntimeException {
    public InvalidTermInMonthsException(String message) {
        super(message);
    }
}
