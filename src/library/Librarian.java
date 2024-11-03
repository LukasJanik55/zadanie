package library;

import java.util.Map;
import java.util.Scanner;
import java.io.InputStream;

import book.Book;

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

        static void listBooks(Map<Book, Integer> books, String title) {
            System.out.println(DIVIDER);
            printTitle(title);
            for (int i = 0; i < books.size(); i++) {
                Book book = (Book) books.keySet().toArray()[i];
                int copies = books.get(book);

                System.out.print("[" + (i + 1) + "] ");
                book.printBasicInfo();
                System.out.println("    " + copies + (copies == 1 ? " copy available" : " copies available") + "\n");
            }
            System.out.println(DIVIDER);
        }

        static void bookInfo(Book book) {
            System.out.println(DIVIDER);
            System.out.println(book);
            System.out.println(DIVIDER);
        }

        static void borrowMessage(Book book) {
            System.out.println("You have borrowed: '" + book.title + "' by " +
                    book.author);
        }

        static void errorMessage(String message) {
            System.out.println(message);
        }

        static void returnMessage(Book book) {
            System.out.println("You have returned: '" + book.title + "' by " +
                    book.author);
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
            // if (bl.isDigitalEquivalentAvailable((PhysicalBook) book)) {
            // System.out.println("But there is a digital version available.");
            // }
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
    public void interactWithUser(InputStream inputStream) {
        CommunicationClass.welcomeMessage(this.name);

        // get available and borrowed books
        Map<Book, Integer> availableBooks = bl.getAvailableBooks();
        Map<Book, Integer> borrowedBooks = bl.getBorrowedBooks();

        // scanner object to get input from the user
        Scanner scanner = new Scanner(inputStream);

        while (true) {
            CommunicationClass.enterCommand();

            // get input from user
            String[] input = scanner.nextLine().strip().split(" ");

            // validate input
            if (!isUserInputValid(input, availableBooks.size(), borrowedBooks.size())) {
                CommunicationClass.invalidCommandMessage();
                continue;
            }

            Command command = Command.fromString(input[0]);
            switch (command) {
                case LIST:
                    CommunicationClass.listBooks(availableBooks, "Available Books");
                    CommunicationClass.listBooks(borrowedBooks, "Borrowed Books");
                    break;
                case INFO:
                    CommunicationClass
                            .bookInfo((Book) availableBooks.keySet().toArray()[Integer.parseInt(input[1]) - 1]);
                    break;
                case BORROW:
                    lendBook((Book) availableBooks.keySet().toArray()[Integer.parseInt(input[1]) - 1]);
                    availableBooks = bl.getAvailableBooks();
                    break;
                case RETURN:
                    returnBook((Book) borrowedBooks.keySet().toArray()[Integer.parseInt(input[1]) - 1]);
                    borrowedBooks = bl.getBorrowedBooks();
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
