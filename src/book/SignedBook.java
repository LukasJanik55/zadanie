package book;

import java.time.LocalDate;

public class SignedBook extends PhysicalBook {
    public final String signer;
    public final LocalDate signedDate;
    public final int signatureNumber;
    public final int signatureCount;

    protected SignedBook(SignedBookBuilder builder) {
        super(builder);
        this.signer = builder.signer;
        this.signedDate = builder.signedDate;
        this.signatureNumber = builder.signatureNumber;
        this.signatureCount = builder.signatureCount;
    }

    public static class SignedBookBuilder extends PhysicalBookBuilder {
        private String signer;
        private LocalDate signedDate;
        private int signatureNumber;
        private int signatureCount;

        public SignedBookBuilder signer(String signer) {
            this.signer = signer;
            return self();
        }

        public SignedBookBuilder signedDate(LocalDate signedDate) {
            this.signedDate = signedDate;
            return self();
        }

        public SignedBookBuilder signatureNumber(int signatureNumber) {
            this.signatureNumber = signatureNumber;
            return self();
        }

        public SignedBookBuilder signatureCount(int signatureCount) {
            this.signatureCount = signatureCount;
            return self();
        }

        @Override
        protected SignedBookBuilder self() {
            return this;
        }

        @Override
        public SignedBook build() {
            if (signer == null) {
                throw new IllegalStateException("Signer is required");
            }

            if (signedDate == null) {
                throw new IllegalStateException("Signed date is required");
            }

            if (signatureNumber <= 0) {
                throw new IllegalStateException("Signature number must be greater than 0");
            }

            if (signatureCount <= 0) {
                throw new IllegalStateException("Signature count must be greater than 0");
            }

            return new SignedBook(this);
        }
    }

    @Override
    public void printBasicInfo() {
        System.out.println("[Signed] " + title + " (" + year + ")" + " - " + author);
    }

    @Override
    public String toString() {
        return "[Signed] " +
                "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Publisher: " + publisher + "\n" +
                "Year: " + year + "\n" +
                "ISBN: " + isbn + "\n" +
                "Number of Pages: " + numberOfPages + "\n" +
                "Condition: " + condition + "\n" +
                "Signed by: " + signer + "\n" +
                "Signature: " + signatureNumber + "/" + signatureCount + "\n" +
                "Signature Date: " + signedDate + "\n";
    }
}
