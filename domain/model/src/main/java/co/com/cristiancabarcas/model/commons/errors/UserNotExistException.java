package co.com.cristiancabarcas.model.commons.errors;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }
}
