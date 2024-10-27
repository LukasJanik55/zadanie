package library;

import java.util.Map;

import book.Book;
import book.PhysicalBook;

public interface BookManagementInterface {
    void addBook(Book book);

    void removeBook(Book book);

    Map<Book, Integer> getAvailableBooks();

    Map<Book, Integer> getBorrowedBooks();

    boolean isBookOwnedByLibrary(Book book);

    boolean isBookAvailable(Book book);

    boolean isDigitalEquivalentAvailable(PhysicalBook book);
}
