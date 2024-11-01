package book;

public enum Condition {
    NEW,
    GOOD,
    FAIR,
    POOR,
    DAMAGED;

    public static Condition fromString(String condition) {
        try {
            return Condition.valueOf(condition.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
