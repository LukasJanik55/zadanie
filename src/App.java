public class App {
    public static void main(String[] args) throws Exception {

        BookLibrary bl = new BookLibrary();

        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084", 18, 336);
        Book book2 = new Book("1984", "George Orwell", 1949, "9780451524935", 20, 328);
        Book book3 = new Book("Pride and Prejudice", "Jane Austen", 1813, "9781503290563", 10, 279);
        Book book4 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 15, 180);
        Book book5 = new Book("The Catcher in the Rye", "J.D. Salinger", 1951, "9780316769488", 12, 214);
        Book book6 = new Book("Moby-Dick", "Herman Melville", 1851, "9781503280786", 14, 635);
        Book book7 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, "9780544003415", 30, 1178);
        Book book8 = new Book("Harry Potter and the Sorcerer's Stone", "J.K.Rowling", 1997, "9780590353427", 25, 309);
        Book book9 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, "9780345339683", 20, 320);
        Book book10 = new Book("Fahrenheit 451", "Ray Bradbury", 1953, "9781451673319", 16, 249);

        bl.addBook(book1); // two instances of book1
        bl.addBook(book1);
        bl.addBook(book2); // three instances of book2
        bl.addBook(book2);
        bl.addBook(book2);
        bl.addBook(book3);
        bl.addBook(book4);
        bl.addBook(book5);
        bl.addBook(book6);
        bl.addBook(book7);
        bl.addBook(book8);
        bl.addBook(book9);
        bl.addBook(book10);

        System.out.println("####################################");
        System.out.println("Welcome to the Library\n");

        System.out.println(bl.getBooks().size());
    }
}
