package library;

import java.io.InputStream;

import book.Book;

public interface LibrarianInterface {
    void listAvailableBooks();

    void addBookToLibrary(Book book);

    void interactWithUser(InputStream inputStream);
}
