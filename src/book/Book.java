package book;

public abstract class Book implements BookInterface {
    public final String title;
    public final String author;
    public final String publisher;
    public final int year;
    public final String isbn;
    public final double value;
    public final int numberOfPages;

    protected Book(BookBuilder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.publisher = builder.publisher;
        this.year = builder.year;
        this.isbn = builder.isbn;
        this.value = builder.value;
        this.numberOfPages = builder.numberOfPages;
    }

    public abstract static class BookBuilder {
        private String title;
        private String author;
        private String publisher;
        private int year;
        private String isbn;
        private double value;
        private int numberOfPages;

        public BookBuilder title(String title) {
            this.title = title;
            return self();
        }

        public BookBuilder author(String author) {
            this.author = author;
            return self();
        }

        public BookBuilder publisher(String publisher) {
            this.publisher = publisher;
            return self();
        }

        public BookBuilder year(int year) {
            this.year = year;
            return self();
        }

        public BookBuilder isbn(String isbn) {
            this.isbn = isbn;
            return self();
        }

        public BookBuilder value(double value) {
            this.value = value;
            return self();
        }

        public BookBuilder numberOfPages(int numberOfPages) {
            this.numberOfPages = numberOfPages;
            return self();
        }

        protected abstract BookBuilder self();

        public abstract Book build();
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Publisher: " + publisher + "\n" +
                "Year: " + year + "\n" +
                "ISBN: " + isbn + "\n" +
                "Value: " + value + "\n" +
                "Number of Pages: " + numberOfPages + "\n";
    }

}
