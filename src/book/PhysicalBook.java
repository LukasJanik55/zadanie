package book;

public class PhysicalBook extends Book {
    public final Condition condition;
    public final CoverType coverMaterial;

    protected PhysicalBook(PhysicalBookBuilder builder) {
        super(builder);
        this.condition = builder.condition;
        this.coverMaterial = builder.materialType;
    }

    public static class PhysicalBookBuilder extends BookBuilder {
        private Condition condition = Condition.NEW;
        private CoverType materialType;

        public PhysicalBookBuilder condition(Condition condition) {
            this.condition = condition;
            return self();
        }

        public PhysicalBookBuilder materialType(CoverType materialType) {
            this.materialType = materialType;
            return self();
        }

        @Override
        protected PhysicalBookBuilder self() {
            return this;
        }

        @Override
        public PhysicalBook build() {
            if (materialType == null) {
                throw new IllegalStateException("Material type is required");
            }

            return new PhysicalBook(this);
        }
    }

    @Override
    public void printInfo() {
        System.out.println(title + " (" + year + ")" + " - " + author + "\n");
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Publisher: " + publisher + "\n" +
                "Year: " + year + "\n" +
                "ISBN: " + isbn + "\n" +
                "Number of Pages: " + numberOfPages + "\n" +
                "Condition: " + condition + "\n" +
                "Cover material: " + coverMaterial + "\n";
    }

}
