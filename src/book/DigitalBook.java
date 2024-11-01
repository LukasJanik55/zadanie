package book;

public class DigitalBook extends Book {

    protected DigitalBook(DigitalBookBuilder builder) {
        super(builder);
    }

    public static class DigitalBookBuilder extends BookBuilder {

        @Override
        public DigitalBook build() {
            return new DigitalBook(this);
        }

        @Override
        protected DigitalBookBuilder self() {
            return this;
        }
    }

}
