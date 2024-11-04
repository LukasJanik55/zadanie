package book;

public class DigitalBook extends Book {
    private final String format;
    private final double fileSize; // in MB
    private boolean isDRMProtected;

    protected DigitalBook(DigitalBookBuilder builder) {
        super(builder);
        this.format = builder.format;
        this.fileSize = builder.fileSize;
        this.isDRMProtected = builder.isDRMProtected;
    }

    public static class DigitalBookBuilder extends BookBuilder {
        private String format;
        private double fileSize;
        private boolean isDRMProtected;

        public DigitalBookBuilder format(String format) {
            this.format = format;
            return self();
        }

        public DigitalBookBuilder fileSize(double fileSize) {
            this.fileSize = fileSize;
            return self();
        }

        public DigitalBookBuilder isDRMProtected(boolean isDRMProtected) {
            this.isDRMProtected = isDRMProtected;
            return self();
        }

        @Override
        protected DigitalBookBuilder self() {
            return this;
        }

        @Override
        public DigitalBook build() {
            if (format == null) {
                throw new IllegalStateException("Format is required");
            }
            return new DigitalBook(this);
        }

    }

    @Override
    public void printBasicInfo() {
        System.out.println("[Digital] " + title + " (" + year + ")" + " - " + author);
    }

    @Override
    public String toString() {
        return "[Digital] " +
                "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Publisher: " + publisher + "\n" +
                "Year: " + year + "\n" +
                "ISBN: " + isbn + "\n" +
                "Number of Pages: " + numberOfPages + "\n" +
                "Format: " + format + "\n" +
                "File size: " + fileSize + " MB" + "\n" +
                "DRM protected: " + isDRMProtected;
    }

}
