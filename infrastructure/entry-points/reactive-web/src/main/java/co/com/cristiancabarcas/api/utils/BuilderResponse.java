package co.com.cristiancabarcas.api.utils;

import co.com.cristiancabarcas.api.dtos.CustomResponse;
import co.com.cristiancabarcas.api.dtos.loans.LoanResponse;
import org.springframework.http.ResponseEntity;

public class BuilderResponse {

    public static ResponseEntity<CustomResponse<LoanResponse>> buildCreatedLoan (LoanResponse loanResponse) {
        return ResponseEntity.ok(CustomResponse.<LoanResponse>builder()
                .data(loanResponse)
                .message("Loan created successfully")
                .success(true)
                .build());
    }

    public static ResponseEntity<CustomResponse<Void>> buildErrorResponse (String message) {
        return ResponseEntity.badRequest().body(CustomResponse.<Void>builder()
                .message(message)
                .success(false)
                .build());
    }
}
