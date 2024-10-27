package book;

public abstract class Book implements BookInterface {
    public final String isbn;
    public final String title;
    public final String author;
    public final int year;
    public final int numberOfPages;
    protected double value;

    protected Book(String title, String author, int year, String isbn, double value, int numberOfPages) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.value = value;
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nAuthor: " + author + "\nYear: " + year + "\nISBN: " + isbn + "\nValue: " + value
                + "\nNumber of Pages: " + numberOfPages + "\n";
    }
}
