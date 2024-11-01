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

        static void listAvailableBooks(Map<Book, Integer> books) {
            System.out.println("-----------------------------\n");
            for (int i = 0; i < books.size(); i++) {
                Book book = (Book) books.keySet().toArray()[i];
                System.out.print("[" + i + "] ");
                book.printInfo();
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

    @Override
    public void interactWithUser(InputStream inputStream) {
        CommunicationClass.welcomeMessage(this.name);

        // get available books
        Map<Book, Integer> books = bl.getAvailableBooks();

        // scanner object to get input from the user
        Scanner scanner = new Scanner(inputStream);

        while (true) {
            // get input from user
            CommunicationClass.enterCommand();

            // TODO: parse command and arguments
            // TODO: validate arguments

            Command command = Command.fromString(scanner.nextLine().toUpperCase());

            // check if the command is valid
            if (command == null) {
                CommunicationClass.invalidCommandMessage();
                continue;
            }

            switch (command) {
                case LIST:
                    CommunicationClass.listAvailableBooks(books);
                    break;
                case INFO:
                    // TODO: finish
                    CommunicationClass.bookInfo((Book) books.keySet().toArray()[0]);
                    break;
                case BORROW:
                    // TODO: finish
                    lendBook((Book) books.keySet().toArray()[0]);
                    break;
                case RETURN:
                    // TODO: finish
                    returnBook((Book) books.keySet().toArray()[0]);
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
