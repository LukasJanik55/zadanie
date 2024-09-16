package library;

import book.Book;

public interface LibrarianInterface {
    void listBooks();

    void listAvailableBooks();

    void addBookToLibrary(Book book);
}
