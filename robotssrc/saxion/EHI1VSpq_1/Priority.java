package saxion.EHI1VSpq_1;

/**
 * Class to make prioritising enemies much easier. Priorities can be:
 * <ul>
 * <li>TEAMMATE: Only to be used for teammates.</li>
 * <li>LOWEST: This enemy will be targeted <i>last of all.</i></li>
 * <li>LOW: This enemy will be targeted after the other enemies, but not last of all.</li>
 * <li>STANDARD: Placeholder priority.</li><
 * <li>HIGH: Usually the enemy currently targeting the leader. Taken out before others.</li>
 * <li>HIGHEST: Enemy leader. Executed before anyone else.</li>
 * </ul>
 */
enum Priority {

    TEAMMATE(0),
    LOWEST(1),
    LOW(2),
    STANDARD(3),
    HIGH(4),
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
