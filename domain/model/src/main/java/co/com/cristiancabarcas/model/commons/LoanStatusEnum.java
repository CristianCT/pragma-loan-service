package co.com.cristiancabarcas.model.commons;

import co.com.cristiancabarcas.model.commons.errors.InvalidStatusException;
import lombok.Getter;

@Getter
public enum LoanStatusEnum {

    PENDING(1, "PENDING", "Pending Approval"),
    APPROVED(2, "APPROVED", "Approved Loan"),
    REJECTED(3, "REJECTED", "Rejected Loan"),
    MANUAL_REVIEW(4, "MANUAL_REVIEW", "Under Manual Review");

    private final int code;
    private final String name;
    private final String description;

    LoanStatusEnum(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static LoanStatusEnum fromCode(int code) {
        for (LoanStatusEnum loanStatusEnum : LoanStatusEnum.values()) {
            if (loanStatusEnum.code == code) {
                return loanStatusEnum;
            }
        }
        throw new InvalidStatusException("Invalid LoanStatusEnum code: " + code);
    }

}
