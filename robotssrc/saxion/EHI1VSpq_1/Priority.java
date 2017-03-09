package saxion.EHI1VSpq_1;

enum Priority {

    LOWEST(1),
    LOW(2),
    STANDARD(3),
    HIGH(4),
    HIGHEST(5);

    private final int p;
    Priority(int p) {
        this.p = p;
    }

    boolean greaterThan(Priority p) {
        return this.p > p.p;
    }

    boolean smallerThan(Priority p) {
        return this.p < p.p;
    }

}
