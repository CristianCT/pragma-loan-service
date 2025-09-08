package co.com.cristiancabarcas.model.loan;

import co.com.cristiancabarcas.model.loantype.LoanType;
import co.com.cristiancabarcas.model.status.LoanStatus;
import co.com.cristiancabarcas.model.user.User;
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
    private User user;
    private LoanType loanType;
    private LoanStatus status;
    private Double amountPerMonth;
}
