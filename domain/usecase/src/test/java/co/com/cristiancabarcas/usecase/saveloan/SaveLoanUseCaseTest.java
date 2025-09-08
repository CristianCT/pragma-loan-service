package co.com.cristiancabarcas.usecase.saveloan;

import co.com.cristiancabarcas.model.commons.LoanStatusEnum;
import co.com.cristiancabarcas.model.commons.LoanTypeEnum;
import co.com.cristiancabarcas.model.commons.errors.InvalidAmountException;
import co.com.cristiancabarcas.model.commons.errors.InvalidLoanTypeException;
import co.com.cristiancabarcas.model.commons.errors.InvalidTermInMonthsException;
import co.com.cristiancabarcas.model.commons.errors.UserNotExistException;
import co.com.cristiancabarcas.model.loan.Loan;
import co.com.cristiancabarcas.model.loan.gateways.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveLoanUseCaseTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private SaveLoanUseCase saveLoanUseCase;

    private Loan loan;

    @Test
    void execute() {

        loan = Loan.builder()
                .amount(1000000000.0)
                .termInMonths(12)
                .loanType(co.com.cristiancabarcas.model.commons.LoanTypeEnum.PERSONAL)
                .status(LoanStatusEnum.PENDING_REVIEW)
                .build();

        when(loanRepository.save(any(Loan.class), any(String.class), any(Integer.class)))
                .thenReturn(Mono.just(loan));

        StepVerifier.create(saveLoanUseCase.execute(loan, "1", 1))
                .assertNext(result -> {
                    assertNotNull(result);
                    assertEquals(1000000000.0, result.getAmount());
                    assertEquals(12, result.getTermInMonths());
                    assertEquals(1, result.getLoanType().getCode());
                    assertEquals(1, result.getStatus().getCode());
                })
                .verifyComplete();

        verify(loanRepository, times(1)).save(any(Loan.class), any(String.class), any(Integer.class));
    }

    @Test
    void executeInvalidLoanType() {

        loan = Loan.builder()
                .amount(1000000000.0)
                .termInMonths(12)
                .loanType(co.com.cristiancabarcas.model.commons.LoanTypeEnum.PERSONAL)
                .status(LoanStatusEnum.PENDING_REVIEW)
                .build();

        StepVerifier.create(saveLoanUseCase.execute(loan, "1", null))
                .expectErrorMatches(InvalidLoanTypeException.class::isInstance)
                .verify();

    }

    @Test
    void executeInvalidAmount() {

        loan = Loan.builder()
                .amount(0.0)
                .termInMonths(12)
                .loanType(LoanTypeEnum.PERSONAL)
                .status(LoanStatusEnum.PENDING_REVIEW)
                .build();

        StepVerifier.create(saveLoanUseCase.execute(loan, "1", 1))
                .expectErrorMatches(InvalidAmountException.class::isInstance)
                .verify();

        loan = Loan.builder()
                .termInMonths(12)
                .loanType(co.com.cristiancabarcas.model.commons.LoanTypeEnum.PERSONAL)
                .status(LoanStatusEnum.PENDING_REVIEW)
                .build();

        StepVerifier.create(saveLoanUseCase.execute(loan, "1", 1))
                .expectErrorMatches(InvalidAmountException.class::isInstance)
                .verify();

    }

    @Test
    void executeInvalidTermInMonths() {

        loan = Loan.builder()
                .amount(1000000000.0)
                .termInMonths(0)
                .loanType(co.com.cristiancabarcas.model.commons.LoanTypeEnum.PERSONAL)
                .status(LoanStatusEnum.PENDING_REVIEW)
                .build();

        StepVerifier.create(saveLoanUseCase.execute(loan, "1", 1))
                .expectErrorMatches(InvalidTermInMonthsException.class::isInstance)
                .verify();

        loan = Loan.builder()
                .amount(1000000000.0)
                .loanType(co.com.cristiancabarcas.model.commons.LoanTypeEnum.PERSONAL)
                .status(LoanStatusEnum.PENDING_REVIEW)
                .build();

        StepVerifier.create(saveLoanUseCase.execute(loan, "1", 1))
                .expectErrorMatches(InvalidTermInMonthsException.class::isInstance)
                .verify();

    }

    @Test
    void executeUserNotExists() {

        loan = Loan.builder()
                .amount(1000000000.0)
                .termInMonths(12)
                .loanType(co.com.cristiancabarcas.model.commons.LoanTypeEnum.PERSONAL)
                .status(LoanStatusEnum.PENDING_REVIEW)
                .build();

        when(loanRepository.save(any(Loan.class), any(String.class), any(Integer.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(saveLoanUseCase.execute(loan, "1", 1))
                .expectErrorMatches(UserNotExistException.class::isInstance)
                .verify();

        verify(loanRepository, times(1)).save(any(Loan.class), any(String.class), any(Integer.class));
    }
}