package library;

import book.Book;

public interface TransferInterface {
    void lendBook(Book book);

    void returnBook(Book book);
}
