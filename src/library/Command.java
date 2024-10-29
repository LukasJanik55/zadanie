package library;

public enum Command {
    LIST,
    BORROW,
    RETURN,
    HELP,
    EXIT;

    public static Command fromString(String command) {
        try {
            return Command.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
