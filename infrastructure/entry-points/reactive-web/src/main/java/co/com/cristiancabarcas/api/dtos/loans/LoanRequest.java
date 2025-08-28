package co.com.cristiancabarcas.api.dtos.loans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {

    private String userId;
    private Double amount;
    private Integer termInMonths;
    private Integer loanType;
    private Integer status;
}
