package model;

import java.util.PriorityQueue;

public class Book {
   private String isbn;
   private String title;
   private String author;
   private int copies;
   private int borrowCount=0;
   private PriorityQueue<WaitRequest> waitingList =new PriorityQueue<>();

   public Book(){

   }

   public Book( String isbn,String title,String author ,int copies){
    this.isbn =isbn;
    this.title = title;
    this.author = author;
    this.copies=copies;
   }
   public String getIsbn(){
    return this.isbn;
   }
   public String getTitle(){
    return this.title;
   }
    public String getAuthor(){
    return this.author;
   }
      public int getCopies(){
    return this.copies;
   }
      public int getBorrowCount(){
    return this.borrowCount;
   }
   public void borrowBook() {
    if (copies > 0) {
        copies--;
        borrowCount++;
    }
}

  public void returnBook() {
    copies++;
  }
  public PriorityQueue<WaitRequest> getWaitingList() {
    return waitingList;
}


   



  





}
