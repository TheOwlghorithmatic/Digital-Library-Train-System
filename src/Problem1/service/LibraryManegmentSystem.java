package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Book;
import model.BorrowRecord;
import model.Borrower;
import model.WaitRequest;
import structure.BookBST;

public class LibraryManegmentSystem {

private BookBST books;

    private ArrayList<Borrower> borrowers;

    private ArrayList<BorrowRecord> borrowRecords;
    public LibraryManegmentSystem(){
        books = new BookBST();

    borrowers = new ArrayList<>();

    borrowRecords = new ArrayList<>();
    }

    public LibraryManegmentSystem(BookBST books , ArrayList<Borrower> borrowers , ArrayList<BorrowRecord> borrowRecords){
        this.books = books;
        this.borrowers = borrowers;
        this.borrowRecords = borrowRecords;
    }
  public ArrayList<Borrower> getBorrowers() {
    return borrowers;
}

public ArrayList<BorrowRecord> getBorrowRecords() {
    return borrowRecords;
}
  public ArrayList<Book> getAllBooks() {
    return books.getAllBooks();
}

    
//methods
public void addBook(Book book){
    books.insert(book);
}

public void removeBook(String isbn){
    books.delete(isbn);
}

public Book searchBook(String isbn){
    return books.search(isbn);
}
private Borrower findBorrower(String id) {
    for (Borrower b : borrowers) {
        if (b.getId().equals(id)) {
            return b;
        }
    }
    return null;
}

public boolean borrowBook(String borrowerId, String isbn,LocalDate borrowDate, LocalDate dueDate) {

    Book book = books.search(isbn);

    if (book == null)
        return false;

    Borrower borrower = findBorrower(borrowerId);

    if (borrower == null)
        return false;

    if (book.getCopies() <= 0) {

    book.getWaitingList().add(
        new WaitRequest(borrower, LocalDate.now())
    );

    return false;
}

    // تعديل بيانات الكتاب
    book.borrowBook();

    // زيادة عدد الكتب المستعارة
    borrower.borrowBook();

    // إنشاء سجل الاستعارة
    BorrowRecord record =new BorrowRecord(borrowerId, isbn, borrowDate, dueDate);

    borrowRecords.add(record);

    return true;
}
public boolean returnBook(String borrowerId, String isbn) {

    Book book = books.search(isbn);

    if (book == null)
        return false;

    Borrower borrower = findBorrower(borrowerId);

    if (borrower == null)
        return false;

    BorrowRecord target = null;

    for (BorrowRecord record : borrowRecords) {

        if (record.getBorrowerId().equals(borrowerId)
                && record.getIsbn().equals(isbn)) {

            target = record;
            break;
        }
    }

    if (target == null)
        return false;

    // إزالة سجل الاستعارة القديم
    borrowRecords.remove(target);

    // تحديث بيانات المستعير الحالي
    borrower.returnBook();

    // هل يوجد أشخاص ينتظرون؟
    if (!book.getWaitingList().isEmpty()) {

        WaitRequest next = book.getWaitingList().poll();

        Borrower nextBorrower = next.getBorrower();

        nextBorrower.borrowBook();

        BorrowRecord newRecord = new BorrowRecord(
                nextBorrower.getId(),
                book.getIsbn(),
                LocalDate.now(),
                LocalDate.now().plusDays(14)
        );

        borrowRecords.add(newRecord);

        // لا نزيد عدد النسخ لأن الكتاب ذهب مباشرة للشخص التالي
    }
    else {

        // لا يوجد أحد ينتظر
        book.returnBook();
    }

    return true;
}
public void addBorrower(Borrower borrower) {
    borrowers.add(borrower);
}

 public void generateReport() {

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("LibraryReport.txt"))) {

        writer.write("=========== LIBRARY REPORT ===========");
        writer.newLine();
        writer.newLine();

        writer.write("BOOKS");
        writer.newLine();
        writer.write("-------------------------------------");
        writer.newLine();

        for (Book book : books.getAllBooks()) {

            writer.write("ISBN: " + book.getIsbn());
            writer.newLine();

            writer.write("Title: " + book.getTitle());
            writer.newLine();

            writer.write("Author: " + book.getAuthor());
            writer.newLine();

            writer.write("Copies: " + book.getCopies());
            writer.newLine();

            writer.write("Borrow Count: " + book.getBorrowCount());
            writer.newLine();

            writer.write("-------------------------------------");
            writer.newLine();
        }

        writer.newLine();
        writer.write("BORROWERS");
        writer.newLine();
        writer.write("-------------------------------------");
        writer.newLine();

        for (Borrower borrower : borrowers) {

            writer.write("ID: " + borrower.getId());
            writer.newLine();

            writer.write("Name: " + borrower.getName());
            writer.newLine();

            writer.write("Graduate: " + borrower.isGraduate());
            writer.newLine();

            writer.write("Borrowed Books: " + borrower.getBorrowedBooksCount());
            writer.newLine();

            writer.write("-------------------------------------");
            writer.newLine();
        }

        writer.newLine();
        writer.write("BORROW RECORDS");
        writer.newLine();
        writer.write("-------------------------------------");
        writer.newLine();

        for (BorrowRecord record : borrowRecords) {

            writer.write("Borrower ID: " + record.getBorrowerId());
            writer.newLine();

            writer.write("ISBN: " + record.getIsbn());
            writer.newLine();

            writer.write("Borrow Date: " + record.getBorrowDate());
            writer.newLine();

            writer.write("Due Date: " + record.getDueDate());
            writer.newLine();

            writer.write("-------------------------------------");
            writer.newLine();
        }

        writer.flush();

        System.out.println("Report Generated Successfully.");

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
