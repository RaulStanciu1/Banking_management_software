package com.bms.routine;

import com.bms.data.Loan;

/**
 * LoanRecord: A record used only by the LoanRoutine
 * @param loanId the id of the loan
 * @param loan the loan information
 */
public record LoanRecord(int loanId, Loan loan) {
}
