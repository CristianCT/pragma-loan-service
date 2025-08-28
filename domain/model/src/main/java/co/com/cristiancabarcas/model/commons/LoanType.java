package co.com.cristiancabarcas.model.commons;

import co.com.cristiancabarcas.model.commons.errors.InvalidLoanTypeException;
import lombok.Getter;

@Getter
public enum LoanType {
    PERSONAL(1, "Personal"),
    MORTGAGE(2, "Mortgage"),
    AUTO(3, "Auto");

    private final int code;
    private final String description;

    LoanType (int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static LoanType fromCode(Integer code) {
        for (LoanType type : LoanType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new InvalidLoanTypeException("Invalid LoanType code: " + code);
    }
}
