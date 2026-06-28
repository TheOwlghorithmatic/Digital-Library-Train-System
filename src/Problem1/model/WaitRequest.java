package model;

import java.time.LocalDate;

public class WaitRequest implements Comparable<WaitRequest> {
    private Borrower borrower;
    private LocalDate requestDate;

    public WaitRequest() {

    }
    
    public WaitRequest(Borrower borrower,LocalDate requestDate) {
        this.borrower = borrower;
        this.requestDate = requestDate;
    }

     public Borrower getBorrower() {
        return borrower;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }
    @Override
public int compareTo(WaitRequest other) {

    // الخريج له أولوية
    if (this.borrower.isGraduate() && !other.borrower.isGraduate())
        return -1;

    if (!this.borrower.isGraduate() && other.borrower.isGraduate())
        return 1;

    // إذا كانا من نفس النوع، الأقدم أولاً
    return this.requestDate.compareTo(other.requestDate);
}
}