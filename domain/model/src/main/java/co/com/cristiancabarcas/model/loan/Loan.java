package co.com.cristiancabarcas.model.loan;

import co.com.cristiancabarcas.model.commons.LoanType;
import co.com.cristiancabarcas.model.commons.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Loan {

    private Double amount;
    private Integer termInMonths;
    private LoanType loanType;
    private Status status;
}
