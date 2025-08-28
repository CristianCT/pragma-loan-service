package co.com.cristiancabarcas.model.commons.errors;

public class InvalidLoanTypeException extends RuntimeException{
    public InvalidLoanTypeException(String message) {
        super(message);
    }
}
