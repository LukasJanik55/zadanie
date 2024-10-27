package library;

import book.Book;

public class Librarian implements LibrarianInterface, TransferInterface {
    private String name;
    private BookLibrary bl;

    public Librarian(String name) {
        this.name = name;
        this.bl = BookLibrary.getInstance();
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
    public void listAvailableBooks() {
        System.out.println("-----------------------------\n");
        bl.getAvailableBooks().forEach((book, count) -> System.out.println(count + "x " + book));
        System.out.println("-----------------------------\n");

    }

    @Override
    public void addBookToLibrary(Book book) {
        bl.addBook(book);
    }

    @Override
    public void lendBook(Book book) {
        if (bl.isBookAvailable(book)) {
            bl.lendBook(book);
            System.out.println("You have borrowed: '" + book.title + "' by " +
                    book.author);
        } else {
            System.out.println("Sorry, this book is not available.");
            // if (bl.isDigitalEquivalentAvailable((PhysicalBook) book)) {
            // System.out.println("But there is a digital version available.");
            // }
        }
    }

    @Override
    public void returnBook(Book book) {
        bl.returnBook(book);
        System.out.println("You have returned: '" + book.title + "' by " +
                book.author);
    }

}
