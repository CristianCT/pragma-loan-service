package co.com.cristiancabarcas.model.commons;

import lombok.Getter;

@Getter
public enum DomainError {
    USER_NOT_EXIST("User does not exist", "User not exist to perform the operation"),
    INVALID_AMOUNT("Invalid amount", "The amount must be greater than zero"),
    INVALID_TERM_IN_MONTHS("Invalid term in months", "The term in months must be between 1 and 60"),
    INVALID_LOAN_TYPE("Invalid loan type", "The loan type must be one of the predefined types");

    private final String type;
    private final String message;

    DomainError(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
