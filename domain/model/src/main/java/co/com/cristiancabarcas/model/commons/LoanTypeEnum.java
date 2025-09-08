package co.com.cristiancabarcas.model.commons;

import co.com.cristiancabarcas.model.commons.errors.InvalidLoanTypeException;
import lombok.Getter;

@Getter
public enum LoanTypeEnum {

    PERSONAL(1, "PERSONAL", "Personal Loan"),
    MORTGAGE(2, "MORTGAGE", "Mortgage Loan"),
    AUTO(3, "AUTO", "Auto Loan"),
    STUDENT(4, "STUDENT", "Student Loan");


    private final int code;
    private final String name;
    private final String description;

    LoanTypeEnum(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static LoanTypeEnum fromCode(Integer code) {
        for (LoanTypeEnum type : co.com.cristiancabarcas.model.commons.LoanTypeEnum.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new InvalidLoanTypeException("Invalid LoanTypeEnum code: " + code);
    }
}
