public class Book {
    private String isbn;
    private String title;
    private String author;
    private int year;
    private int value;
    private int numberOfPages;
    private boolean isBorrowed;

    public Book(String title, String author, int year, String isbn, int value, int numberOfPages) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.value = value;
        this.numberOfPages = numberOfPages;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getValue() {
        return value;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public Boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", value=" + value +
                ", numberOfPages=" + numberOfPages +
                '}';
    }

}
