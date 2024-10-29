import book.Book;
import book.PhysicalBook;
import book.SignedBook;
import library.Librarian;

public class App {
        public static void main(String[] args) throws Exception {
                // initialize the librarian
                Librarian librarian = new Librarian("Berry");

                // create some books and add them to the library
                Book book1 = new PhysicalBook("1984", "George Orwell", 1949, "9780451524935", 20, 328);
                Book book11 = new SignedBook("1984", "George Orwell", 1949, "9780451524935", 20, 328);
                Book book2 = new PhysicalBook("Pride and Prejudice", "Jane Austen", 1813, "9781503290563", 10, 279);
                Book book3 = new PhysicalBook("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 15,
                                180);
                Book book4 = new SignedBook("The Lord of the Rings", "J.R.R. Tolkien", 1954, "9780544003415", 30, 1178);
                Book book5 = new PhysicalBook("The Hobbit", "J.R.R. Tolkien", 1937, "9780345339683", 20, 320);
                // Book digitalBook = new DigitalBook("Pride and Prejudice", "Jane Austen",
                // 1813, "9781503290563", 10,
                // 279);

                librarian.addBookToLibrary(book1); // two instances of book1
                librarian.addBookToLibrary(book1);
                librarian.addBookToLibrary(book11);
                librarian.addBookToLibrary(book2);
                librarian.addBookToLibrary(book3);
                librarian.addBookToLibrary(book4);
                librarian.addBookToLibrary(book5);
                // librarian.addBookToLibrary(digitalBook);

                // start user interaction
                librarian.interactWithUser(System.in);
        }
}
