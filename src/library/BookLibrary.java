package library;

import java.util.ArrayList;
import book.Book;
import book.PhysicalBook;
import book.DigitalBook;

public class BookLibrary implements ManagementInterface {
    private static BookLibrary instance;
    private ArrayList<Book> books;

    private BookLibrary() {
        books = new ArrayList<>();
    }

    public static BookLibrary getInstance() {
        if (instance == null) {
            instance = new BookLibrary();
        }
        return instance;
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void removeBook(Book book) {
        books.remove(book);
    }

    @Override
    public ArrayList<Book> getBooks() {
        return books;
    }

    @Override
    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (!book.isBorrowed()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    @Override
    public ArrayList<Book> getBorrowedBooks() {
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isBorrowed()) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }

    @Override
    public boolean isBookAvailable(Book book) {
        return !book.isBorrowed();
    }

    @Override
    public boolean isDigitalEquivalentAvailable(PhysicalBook physicalBook) {
        for (Book book : books) {
            if (book.isbn.equals(physicalBook.isbn) && book instanceof DigitalBook) {
                return true;
            }
        }
        return false;
    }

}
