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

        static void welcomeMessage(String name) {
            System.out.println("####################################");
            System.out.println("Welcome to the Library");
            System.out.println("My name is " + name + ", how may I help you?\n");

            commandList();

            System.out.println("####################################\n");
        }

        static void enterCommand() {
            System.out.print("\nEnter a command: ");
        }

        static void commandList() {
            System.out.println("To list all available books, type 'list'");
            System.out.println("For more information about a book, type 'info' with index of the book, e.g. 'info 1'");
            System.out.println("To borrow a book, type 'borrow' with index of the book, e.g. 'borrow 1'");
            System.out.println("To return a book, type 'return' with index of the book, e.g. 'return 1'");
            System.out.println("To exit the library, type 'exit'");
            System.out.println("To check available commands, type 'help'");
        }

        static void listBooks(Map<Book, Integer> books) {
            System.out.println("-----------------------------\n");
            for (int i = 0; i < books.size(); i++) {
                Book book = (Book) books.keySet().toArray()[i];
                int copies = books.get(book);

                System.out.print("[" + (i + 1) + "] ");
                book.printBasicInfo();

                if (copies == 1) {
                    System.out.println("    " + copies + " copy available");
                } else {
                    System.out.println("    " + copies + " copies available");
                }
                System.out.println();
            }
            System.out.println("-----------------------------");
        }

        static void bookInfo(Book book) {
            System.out.println(book);
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
            System.out.println("Available commands:");
            commandList();
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

        // validate commands
        if (command == Command.LIST || command == Command.HELP || command == Command.EXIT) {
            // check if command is unnecessarily followed by an argument
            if (input.length > 1) {
                return false;
            }
        } else {
            // check if argument value is present
            if (input.length != 2) {
                return false;
            }

            // check if argument is an integer
            try {
                Integer.parseInt(input[1]);
            } catch (NumberFormatException e) {
                return false;
            }

        }

        // check if the book index is within the size of books collection
        if (command == Command.BORROW) {
            if (Integer.parseInt(input[1]) < 1 || Integer.parseInt(input[1]) > availableBooksSize) {
                return false;
            }
        } else if (command == Command.RETURN) {
            if (Integer.parseInt(input[1]) < 1 || Integer.parseInt(input[1]) > borrowedBooksSize) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void interactWithUser(InputStream inputStream) {
        CommunicationClass.welcomeMessage(this.name);

        // get available books
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
                    CommunicationClass.listBooks(availableBooks);
                    CommunicationClass.listBooks(borrowedBooks);
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
