package model;

public class Borrower {
    private String id;
    private String name;
    private boolean graduate;
    private int borrowedBooksCount=0;

    public Borrower(){

    }
     public Borrower(String id,String name , boolean graduate ){
        this.id = id;
        this.name = name;
        this.graduate = graduate;
    }
public void borrowBook() {
    borrowedBooksCount++;
}

public void returnBook() {
    if (borrowedBooksCount > 0) {
        borrowedBooksCount--;
    }
}
    public String getId(){
        return this.id;
    }
      public String getName(){
        return this.name;
    }
      public boolean isGraduate(){
        return this.graduate;
    }
        public int getBorrowedBooksCount(){
        return this.borrowedBooksCount;
    }


}
