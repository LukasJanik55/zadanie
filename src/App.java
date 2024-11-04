import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import book.Book;
import book.CoverType;
import book.DigitalBook.DigitalBookBuilder;
import book.PhysicalBook.PhysicalBookBuilder;
import book.SignedBook.SignedBookBuilder;
import library.Librarian;

public class App {
        public static void main(String[] args) throws Exception {
                // initialize the librarian
                Librarian librarian = new Librarian("Berry");

                // initialize book builders
                PhysicalBookBuilder physicalBookBuilder = new PhysicalBookBuilder();
                SignedBookBuilder signedBookBuilder = new SignedBookBuilder();
                DigitalBookBuilder digitalBookBuilder = new DigitalBookBuilder();

                // create some books
                Book book1 = physicalBookBuilder
                                .materialType(CoverType.PAPERBACK)
                                .title("1984")
                                .author("George Orwell")
                                .publisher("Secker & Warburg")
                                .year(1949)
                                .isbn("9780451524935")
                                .numberOfPages(328)
                                .build();

                Book book2 = physicalBookBuilder
                                .materialType(CoverType.HARDCOVER)
                                .title("Pride and Prejudice")
                                .author("Jane Austen")
                                .publisher("T. Egerton, Whitehall")
                                .year(1813)
                                .isbn("9781503290563")
                                .numberOfPages(279)
                                .build();

                Book book3 = physicalBookBuilder
                                .materialType(CoverType.LEATHER_BOUND)
                                .title("The Great Gatsby")
                                .author("F. Scott Fitzgerald")
                                .publisher("Charles Scribner's Sons")
                                .year(1925)
                                .isbn("9780743273565")
                                .numberOfPages(180)
                                .build();

                Book book4 = signedBookBuilder
                                .signer("J.R.R. Tolkien")
                                .signedDate(LocalDate.of(2024, 5, 18))
                                .signatureNumber(1)
                                .signatureCount(3)
                                .materialType(CoverType.HARDCOVER)
                                .title("The Lord of the Rings")
                                .author("J.R.R. Tolkien")
                                .publisher("George Allen & Unwin")
                                .year(1954)
                                .isbn("9780544003415")
                                .numberOfPages(1178)
                                .build();

                Book book5 = physicalBookBuilder
                                .materialType(CoverType.PAPERBACK)
                                .title("The Hobbit")
                                .author("J.R.R. Tolkien")
                                .publisher("George Allen & Unwin")
                                .year(1937)
                                .isbn("9780345339683")
                                .numberOfPages(320)
                                .build();

                Book digitalBook = digitalBookBuilder
                                .format("EPUB")
                                .fileSize(2.5)
                                .isDRMProtected(true)
                                .title("The Hobbit")
                                .author("J.R.R. Tolkien")
                                .publisher("George Allen & Unwin")
                                .year(1937)
                                .isbn("9780345339683")
                                .numberOfPages(320)
                                .build();

                // add books to the library
                librarian.addBookToLibrary(book1); // two instances of book1
                librarian.addBookToLibrary(book1);
                librarian.addBookToLibrary(book2);
                librarian.addBookToLibrary(book3);
                librarian.addBookToLibrary(book4);
                librarian.addBookToLibrary(book5);
                librarian.addBookToLibrary(digitalBook);

                // start user interaction
                Set<Book> userBorrowedBooks = new HashSet<>();
                librarian.interactWithUser(System.in, userBorrowedBooks);
        }
}
