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
public class RevisionLoanResponseItem {
    private Double amount;
    private Integer termInMonths;
    private String email;
    private String name;
    private String loanType;
    private Double taxRate;
    private String status;
    private Double baseSalary;
    private Double amountPerMonth;
}
