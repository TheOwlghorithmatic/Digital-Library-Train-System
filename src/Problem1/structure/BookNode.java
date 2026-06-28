package structure;

import model.Book;

public class BookNode {
   private Book book;
   private BookNode left;
   private BookNode right;
   private int height;

    public BookNode(Book book){
        this.book = book;
        left=null;
        right = null;
        height = 1;
    }
       public Book getBook() {
        return book;
    }

    public BookNode getLeft() {
        return left;
    }

    public void setLeft(BookNode left) {
        this.left = left;
    }

    public BookNode getRight() {
        return right;
    }

    public void setRight(BookNode right) {
        this.right = right;
    }
    public void setBook(Book book) {
    this.book = book;
}
public int getHeight() {
    return height;
}

public void setHeight(int height) {
    this.height = height;
}
}

