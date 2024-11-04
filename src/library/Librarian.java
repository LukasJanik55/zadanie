package library;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.InputStream;

import book.Book;
import book.DigitalBook;

public class Librarian implements LibrarianInterface, TransferInterface {
    private String name;
    private BookLibrary bl;

    public Librarian(String name) {
        this.name = name;
        this.bl = BookLibrary.getInstance();
    }

    private abstract class CommunicationClass {
        private static final String COMMANDS = "To list all available books, type 'list'\n" +
                "For more information about a book, type 'info' with the index, e.g. 'info 1'\n" +
                "To borrow a book, type 'borrow' with the index, e.g. 'borrow 1'\n" +
                "To return a book, type 'return' with the index, e.g. 'return 1'\n" +
                "To see available commands, type 'help'\n" +
                "To exit, type 'exit'";

        private static final String DIVIDER = "-----------------------------";

        static void welcomeMessage(String name) {
            System.out.println(DIVIDER);
            System.out.println("Welcome to the Library");
            System.out.println("My name is " + name + ", how may I help you?\n");
            System.out.println(COMMANDS);
            System.out.println(DIVIDER);
        }

        static void enterCommand() {
            System.out.print("\nEnter a command: ");
        }

        static void printTitle(String title) {
            System.out.println("            [" + title + "]\n");
        }

        static void listAvailableBooks(Map<Book, Integer> books, String title) {
            System.out.println(DIVIDER);
            printTitle(title);
            int i = 0;
            for (Map.Entry<Book, Integer> entry : books.entrySet()) {
                Book book = entry.getKey();
                int copies = entry.getValue();

                System.out.print("[" + (i + 1) + "] ");
                book.printBasicInfo();
                if (!(book instanceof DigitalBook)) {
                    System.out.println("    " + copies + (copies == 1 ? " copy available" : " copies available"));
                }
                System.out.println();

                i++;
            }
            System.out.println(DIVIDER);
        }

        static void listUserBooks(Set<Book> books, String title) {
            System.out.println(DIVIDER);
            printTitle(title);
            int i = 0;
            for (Book book : books) {
                System.out.print("[" + (i + 1) + "] ");
                book.printBasicInfo();
                System.out.println();
                i++;
            }
            System.out.println(DIVIDER);
        }

        static void bookInfo(Book book) {
            System.out.println(DIVIDER);
            System.out.println(book);
            System.out.println(DIVIDER);
        }

        static void borrowMessage(Book book) {
            System.out.print("You have borrowed: ");
            book.printBasicInfo();
        }

        static void errorMessage(String message) {
            System.out.println(message);
        }

        static void returnMessage(Book book) {
            System.out.print("You have returned: ");
            book.printBasicInfo();
        }

        static void exitMessage() {
            System.out.println("Goodbye!");
        }

        static void showHelp() {
            System.out.println(DIVIDER);
            printTitle("Available commands");
            System.out.println(COMMANDS);
            System.out.println(DIVIDER);
        }

        static void invalidCommandMessage() {
            System.out.println("Invalid command. Type 'help' to see available commands.");
        }
    }

    @Override
    public void addBookToLibrary(Book book) {
        bl.addBook(book);
    }

    @Override
    public void lendBook(Book book) {
        try {
            bl.lendBook(book);
            CommunicationClass.borrowMessage(book);
        } catch (IllegalArgumentException e) {
            CommunicationClass.errorMessage(e.getMessage());
        }
    }

    @Override
    public void returnBook(Book book) {
        try {
            bl.returnBook(book);
            CommunicationClass.returnMessage(book);
        } catch (IllegalArgumentException e) {
            CommunicationClass.errorMessage(e.getMessage());
        }
    }

    private boolean isUserInputValid(String[] input, int availableBooksSize, int borrowedBooksSize) {
        // validate input length
        if (input.length == 0 || input.length > 2) {
            return false;
        }

        // check validity of command
        Command command = Command.fromString(input[0]);
        if (command == null) {
            return false;
        }

        // validate single-word commands
        if (command == Command.LIST || command == Command.HELP || command == Command.EXIT) {
            return input.length == 1;
        }

        // validate two-word command length
        if (input.length != 2) {
            return false;
        }

        // check if argument is an integer
        int bookIndex;
        try {
            bookIndex = Integer.parseInt(input[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        // validate index bounds
        if (command == Command.INFO && (bookIndex < 1 || bookIndex > availableBooksSize)) {
            return false;
        }
        if (command == Command.BORROW && (bookIndex < 1 || bookIndex > availableBooksSize)) {
            return false;
        }
        if (command == Command.RETURN && (bookIndex < 1 || bookIndex > borrowedBooksSize)) {
            return false;
        }

        return true;
    }

    @Override
    public void interactWithUser(InputStream inputStream, Set<Book> userBorrowedBooks) {
        CommunicationClass.welcomeMessage(this.name);

        // get available books
        Map<Book, Integer> availableBooks = bl.getAvailableBooks();

        // scanner object to get input from the user
        Scanner scanner = new Scanner(inputStream);

        while (true) {
            CommunicationClass.enterCommand();

            // get input from user
            String[] input = scanner.nextLine().strip().split(" ");

            // validate input
            if (!isUserInputValid(input, availableBooks.size(), userBorrowedBooks.size())) {
                CommunicationClass.invalidCommandMessage();
                continue;
            }

            Book book = null;
            Command command = Command.fromString(input[0]);
            switch (command) {
                case LIST:
                    CommunicationClass.listAvailableBooks(availableBooks, "Available Books");
                    CommunicationClass.listUserBooks(userBorrowedBooks, "Borrowed Books");
                    break;
                case INFO:
                    CommunicationClass
                            .bookInfo((Book) availableBooks.keySet().toArray()[Integer.parseInt(input[1]) - 1]);
                    break;
                case BORROW:
                    // get book from available books
                    book = ((Book) availableBooks.keySet().toArray()[Integer.parseInt(input[1]) - 1]);

                    // check if the user has already borrowed the book
                    if (userBorrowedBooks.contains(book)) {
                        CommunicationClass.errorMessage("You have already borrowed this book.");
                        break;
                    }

                    // lend the book
                    lendBook(book);
                    availableBooks = bl.getAvailableBooks();
                    userBorrowedBooks.add(book);
                    break;
                case RETURN:
                    book = (Book) userBorrowedBooks.toArray()[Integer.parseInt(input[1]) - 1];
                    returnBook(book);
                    userBorrowedBooks.remove(book);
                    break;
                case HELP:
                    CommunicationClass.showHelp();
                    break;
                case EXIT:
                    CommunicationClass.exitMessage();
                    break;
            }

            // exit the loop if the user types 'exit' since the break inside the switch
            // statement will only break the switch statement
            if (command == Command.EXIT) {
                break;
            }
        }

        scanner.close();
    }

}
