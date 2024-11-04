package library;

import java.util.HashMap;
import java.util.Map;

import book.Book;
import book.DigitalBook;

public class BookLibrary implements BookManagementInterface, TransferInterface {
    private static BookLibrary instance;
    private Map<Book, Integer> availableBooks;
    private Map<Book, Integer> borrowedBooks;

    private BookLibrary() {
        availableBooks = new HashMap<>();
        borrowedBooks = new HashMap<>();
    }

    public static BookLibrary getInstance() {
        if (instance == null) {
            instance = new BookLibrary();
        }
        return instance;
    }

    @Override
    public void addBook(Book book) {
        if (book instanceof DigitalBook) {
            availableBooks.put(book, 1); // digital books are always available
        } else {
            availableBooks.put(book, availableBooks.getOrDefault(book, 0) + 1);
        }
    }

    @Override
    public void removeBook(Book book) {
        // remove book from available books (for example damaged beyond repair)
        if (availableBooks.containsKey(book)) {
            if (availableBooks.get(book) > 1) {
                availableBooks.put(book, availableBooks.get(book) - 1);
                return;
            }
            availableBooks.remove(book);
        }

        // remove book from borrowed books (for example lost)
        if (borrowedBooks.containsKey(book)) {
            if (borrowedBooks.get(book) > 1) {
                borrowedBooks.put(book, borrowedBooks.get(book) - 1);
                return;
            }
            borrowedBooks.remove(book);
        }
    }

    @Override
    public Map<Book, Integer> getAvailableBooks() {
        return availableBooks;
    }

    @Override
    public Map<Book, Integer> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public boolean isBookOwnedByLibrary(Book book) {
        return availableBooks.containsKey(book) || borrowedBooks.containsKey(book);
    }

    @Override
    public boolean isBookAvailable(Book book) {
        return isBookOwnedByLibrary(book) && availableBooks.get(book) > 0;
    }

    @Override
    public void lendBook(Book book) {
        if (!isBookAvailable(book)) {
            throw new IllegalArgumentException("Sorry, this book is not available.");
        }

        // if book is not digital, decrease available count
        if (!(book instanceof DigitalBook)) {
            availableBooks.put(book, availableBooks.get(book) - 1);
            if (availableBooks.get(book) == 0) {
                availableBooks.remove(book);
            }
        }

        // increase borrowed count
        borrowedBooks.put(book, borrowedBooks.getOrDefault(book, 0) + 1);
    }

    @Override
    public void returnBook(Book book) {
        if (!borrowedBooks.containsKey(book)) {
            throw new IllegalArgumentException("This book is not borrowed");
        }

        // if book is not digital, increase available count
        if (!(book instanceof DigitalBook)) {
            availableBooks.put(book, availableBooks.getOrDefault(book, 0) + 1);
        }

        // decrease borrowed count
        borrowedBooks.put(book, borrowedBooks.get(book) - 1);
        if (borrowedBooks.get(book) == 0) {
            borrowedBooks.remove(book);
        }
    }

}
