package srg.ship;

/**
 * Enumerates the quality tiers for Rooms.
 * @version 1.0
 * @ass1
 */
public enum RoomTier {
    /**
     * The most basic tier of Room. Has healthMultiplier of 10 and damageMultiplier of 2.
     */
    BASIC(10, 2),
    /**
     * The second tier of Room. Has healthMultiplier of 20 and damageMultiplier of 2.
     */
    AVERAGE(20, 2),
    /**
     * The final tier of Room. Has healthMultiplier of 40 and damageMultiplier of 1.
     */
    PRIME(40, 1);

    /**
     * Room quality tier influences the health of a room.
     * @ass1
     */
    public int healthMultiplier;
    /**
     * Room quality tier influences the rate at which it takes damage.
     * @ass1
     */
    public int damageMultiplier;

    /**
     * Constructor which sets healthMultiplier and damageMultiplier for this RoomTier.
     *
     * @param healthMultiplier  The health multiplier for this RoomTier.
     * @param damageMultiplier  The damage multiplier for this RoomTier.
     *
     * @ass1
     */
    RoomTier(int healthMultiplier, int damageMultiplier) {
        this.healthMultiplier = healthMultiplier;
        this.damageMultiplier = damageMultiplier;
    }
}
