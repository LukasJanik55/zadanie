package library;

import java.io.InputStream;
import java.util.Set;

import book.Book;

public interface LibrarianInterface {
    void addBookToLibrary(Book book);

    void interactWithUser(InputStream inputStream, Set<Book> borrowedBooks);
}
