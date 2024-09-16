package library;

import java.util.ArrayList;

import book.Book;
import book.DigitalBook;
import book.PhysicalBook;

public class Librarian implements LibrarianInterface, TransferInterface {
    private String name;
    private BookLibrary bl;

    public Librarian(String name, BookLibrary bl) {
        this.name = name;
        this.bl = bl;
        init();
    }

    private void init() {
        System.out.println("####################################");
        System.out.println("Welcome to the Library");
        System.out.println("My name is " + this.name + ", how may I help you?\n");

        System.out.println("To list all books, type 'list'");
        System.out.println("To list all available books, type 'available'");
        System.out.println("To borrow a book, type 'borrow'");
        System.out.println("To return a book, type 'return'\n");
    }

    @Override
    public void listBooks() {
        ArrayList<Book> books = bl.getBooks();
        // for (Book book : books) {
        // System.out.println(book);
        // }
        System.out.println("There are " + books.size() + " books in the library.");
    }

    @Override
    public void listAvailableBooks() {
        ArrayList<Book> books = bl.getAvailableBooks();
        // for (Book book : books) {
        // System.out.println(book);
        // }
        System.out.println("There are " + books.size() + " books available in the library.");

    }

    @Override
    public void addBookToLibrary(Book book) {
        bl.addBook(book);
    }

    @Override
    public void lendBook(Book book) {
        if (book instanceof DigitalBook) {
            // TODO: do digital book lend
            return;
        }

        if (bl.isBookAvailable(book)) {
            book.setBorrowed(true);
            System.out.println("You have borrowed: '" + book.title + "' by " + book.author);
        } else {
            System.out.println("Sorry, this book is not available.");
            if (bl.isDigitalEquivalentAvailable((PhysicalBook) book)) {
                System.out.println("But there is a digital version available.");
            }
        }
    }

    @Override
    public void returnBook(Book book) {
        if (bl.isBookAvailable(book)) {
            System.out.println("This book should be in the library.");
        } else {
            book.setBorrowed(false);
            System.out.println("You have returned: " + book);
        }
    }

}
