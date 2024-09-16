package library;

import java.util.ArrayList;

import book.Book;
import book.PhysicalBook;

public interface ManagementInterface {
    void addBook(Book book);

    void removeBook(Book book);

    ArrayList<Book> getBooks();

    ArrayList<Book> getAvailableBooks();

    ArrayList<Book> getBorrowedBooks();

    boolean isBookAvailable(Book book);

    boolean isDigitalEquivalentAvailable(PhysicalBook book);
}
