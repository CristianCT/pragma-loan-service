package co.com.cristiancabarcas.model.loantype;

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
public class LoanType {
    private String name;
    private String description;
    private String minimumAmount;
    private String maximumAmount;
    private Double taxRate;
    private Boolean automaticApproval;
}
