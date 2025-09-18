package co.com.cristiancabarcas.model.commons.errors;

public class UserInvalidLoanException extends RuntimeException {
    public UserInvalidLoanException(String message) {
        super(message);
    }
}
