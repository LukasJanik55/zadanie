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
            System.out.println("To borrow a book, type 'borrow'");
            System.out.println("To return a book, type 'return'");
            System.out.println("To exit the library, type 'exit'");
            System.out.println("To check available commands, type 'help'");
        }

        static void listAvailableBooks(Map<Book, Integer> books) {
            System.out.println("-----------------------------\n");
            books.forEach((book, count) -> System.out.println(count + "x " + book));
            System.out.println("-----------------------------");
        }

        static void borrowMessage(Book book) {
            System.out.println("You have borrowed: '" + book.title + "' by " +
                    book.author);
        }

        static void unavailableMessage() {
            System.out.println("Sorry, this book is not available.");
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
    public void listAvailableBooks() {
        Map<Book, Integer> books = bl.getAvailableBooks();
        CommunicationClass.listAvailableBooks(books);
    }

    @Override
    public void addBookToLibrary(Book book) {
        bl.addBook(book);
    }

    @Override
    public void lendBook(Book book) {
        if (bl.isBookAvailable(book)) {
            bl.lendBook(book);
            CommunicationClass.borrowMessage(book);
        } else {
            CommunicationClass.unavailableMessage();
            // if (bl.isDigitalEquivalentAvailable((PhysicalBook) book)) {
            // System.out.println("But there is a digital version available.");
            // }
        }
    }

    @Override
    public void returnBook(Book book) {
        bl.returnBook(book);
        CommunicationClass.returnMessage(book);
    }

    @Override
    public void interactWithUser(InputStream inputStream) {
        CommunicationClass.welcomeMessage(this.name);
        Scanner scanner = new Scanner(inputStream);

        while (true) {
            // get input from user
            CommunicationClass.enterCommand();
            Command command = Command.fromString(scanner.nextLine().toUpperCase());

            // check if the command is valid
            if (command == null) {
                CommunicationClass.invalidCommandMessage();
                continue;
            }

            switch (command) {
                case LIST:
                    listAvailableBooks();
                    break;
                case BORROW:
                    // lendBook(1);
                    break;
                case RETURN:
                    // returnBook(1);
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
