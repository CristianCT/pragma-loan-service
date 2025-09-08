package co.com.cristiancabarcas.model.commons.errors;

public class LoansEmptyException extends RuntimeException {
    public LoansEmptyException(String message) {
        super(message);
    }
}
