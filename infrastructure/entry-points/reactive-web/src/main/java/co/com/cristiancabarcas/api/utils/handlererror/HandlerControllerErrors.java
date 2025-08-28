package co.com.cristiancabarcas.api.utils.handlererror;

import co.com.cristiancabarcas.api.dtos.CustomResponse;
import co.com.cristiancabarcas.api.utils.BuilderResponse;
import co.com.cristiancabarcas.model.commons.errors.InvalidAmountException;
import co.com.cristiancabarcas.model.commons.errors.InvalidLoanTypeException;
import co.com.cristiancabarcas.model.commons.errors.InvalidStatusException;
import co.com.cristiancabarcas.model.commons.errors.InvalidTermInMonthsException;
import co.com.cristiancabarcas.model.commons.errors.UserNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Logger;

@RestControllerAdvice
public class HandlerControllerErrors {

    private static final Logger log = Logger.getLogger(HandlerControllerErrors.class.getName());

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(InvalidAmountException ex) {
        log.warning(ex.getMessage());
        return BuilderResponse.buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidLoanTypeException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(InvalidLoanTypeException ex) {
        log.warning(ex.getMessage());
        return BuilderResponse.buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(InvalidStatusException ex) {
        log.warning(ex.getMessage());
        return BuilderResponse.buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidTermInMonthsException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(InvalidTermInMonthsException ex) {
        log.warning(ex.getMessage());
        return BuilderResponse.buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(UserNotExistException ex) {
        log.warning(ex.getMessage());
        return BuilderResponse.buildErrorResponse(ex.getMessage());
    }


}
