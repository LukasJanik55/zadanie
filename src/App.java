import book.Book;
import book.DamagedBook;
import book.DamagedSignedBook;
import book.DigitalBook;
import book.PhysicalBook;
import book.SignedBook;
import library.BookLibrary;
import library.Librarian;

public class App {
    public static void main(String[] args) throws Exception {
        // initialize the library and pass it to the librarian
        BookLibrary bl = BookLibrary.getInstance();
        Librarian librarian = new Librarian("Berry", bl);

        // create some books and add them to the library
        Book book1 = new PhysicalBook("1984", "George Orwell", 1949, "9780451524935", 20, 328);
        Book book2 = new PhysicalBook("Pride and Prejudice", "Jane Austen", 1813, "9781503290563", 10, 279);
        Book book3 = new DamagedBook("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 15, 180);
        Book book4 = new SignedBook("The Lord of the Rings", "J.R.R. Tolkien", 1954, "9780544003415", 30, 1178);
        Book book5 = new DamagedSignedBook("The Hobbit", "J.R.R. Tolkien", 1937, "9780345339683", 20, 320);
        Book digitalBook = new DigitalBook("Pride and Prejudice", "Jane Austen", 1813, "9781503290563", 10, 279);

        librarian.addBookToLibrary(book1); // two instances of book1
        librarian.addBookToLibrary(book1);
        librarian.addBookToLibrary(book2);
        librarian.addBookToLibrary(book3);
        librarian.addBookToLibrary(book4);
        librarian.addBookToLibrary(book5);
        librarian.addBookToLibrary(digitalBook);

        // set default lent books
        librarian.lendBook(book2);
        librarian.lendBook(book4);

        // list all books
        librarian.listBooks();

        // list all available books
        librarian.listAvailableBooks();

        librarian.lendBook(book2);

    }
}
