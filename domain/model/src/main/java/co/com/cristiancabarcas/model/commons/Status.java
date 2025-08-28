package co.com.cristiancabarcas.model.commons;

import co.com.cristiancabarcas.model.commons.errors.InvalidStatusException;
import lombok.Getter;

@Getter
public enum Status {
    PENDING_REVIEW(1, "Pending Review"),
    REVIEWED(2, "Reviewed"),
    APPROVED(3, "Approved"),
    REJECTED(4, "Rejected");

    private final int code;
    private final String description;

    Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Status fromCode(int code) {
        for (Status status : Status.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new InvalidStatusException("Invalid Status code: " + code);
    }

}
