package saxion.EHI1VSpq1;

/**
 * Enum to make prioritising enemies much easier. Priorities can be:
 *
 * @author Erik
 */
enum Priority {

    /**
     * Lowest priority, only used for teammates. Will never be targeted.
     */
    TEAMMATE(0),
    /**
     * Lowest priority, not yet used. Can be used for robots that should not be targeted unless absolutely necessary.
     */
    LOWEST(1),
    /**
     * Lower priority, not yet used either.
     */
    LOW(2),
    /**
     * Placeholder priority, assigned to every robot unless defined otherwise.
     */
    STANDARD(3),
    /**
     * High priority, usually the enemy currently targeting the leader (VladimirPutin). Taken out before others.
     */
    HIGH(4),
    /**
     * Enemy leader. Taken out before anything else, with anything it takes.
     */
    HIGHEST(5);

    private final int p;

    Priority(int p) {
        this.p = p;
    }

    /**
     * Priority is greater than <i>p</i>.
     * @param p Priority to compare.
     * @return Whether the priority is above <i>p</i>.
     */
    boolean greaterThan(Priority p) {
        return this.p >= p.p;
    }

}
