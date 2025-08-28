package co.com.cristiancabarcas.model.commons.errors;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String message) {
        super(message);
    }
}
