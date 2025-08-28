package co.com.cristiancabarcas.model.commons.errors;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
