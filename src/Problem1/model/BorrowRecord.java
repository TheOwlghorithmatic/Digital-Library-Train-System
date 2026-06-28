package model;

import java.time.LocalDate;

public class BorrowRecord {
    private String borrowerId;
    private String isbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;


    public BorrowRecord(){
        
    }

    public BorrowRecord(String borrowerId ,String isbn , LocalDate borrowDate , LocalDate dueDate){
        this.borrowerId = borrowerId;
        this.isbn = isbn;
        this.borrowDate = borrowDate;
        this.dueDate =dueDate;
    }

    public String getBorrowerId(){
        return this.borrowerId;
    }
      public String getIsbn(){
        return this.isbn;
    }
    public LocalDate getBorrowDate(){
        return this.borrowDate;
    }
     public LocalDate getDueDate(){
        return this.dueDate;
    }

}
