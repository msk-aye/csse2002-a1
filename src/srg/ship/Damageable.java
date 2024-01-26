package srg.ship;

/**
 * Interface to model damageable classes. Classes which implement Damageable are capable of
 * experiencing damage. Damageable objects have a health stat which can take damage and be repaired.
 */
public interface Damageable {
    /**
     * The rate at which damage occurs to a Damageable object is determined by the DAMAGE_RATE
     * and some feature of the object's class.
     */
    static final int DAMAGE_RATE = 5;
    /**
     * The maximum health of a Damageable object is determined by the HEALTH_MULTIPLIER and some
     * feature of the object's class.
     */
    static final int HEALTH_MULTIPLIER = 5;
    /** The threshold at which a Damageable object must be repaired. */
    static final int REPAIR_THRESHOLD = 30;

    /**
     * Returns the health as an integer percentage of the maximum. Health may become negative, in
     * which case the percentage is also negative.
     * @return An integer representing the current health as a percentage of the maximum, rounded
     *         down.
     */
    int getHealth();

    /**
     * Returns whether the current object needs repair. If the percentage health (rounded-down)
     * is less than or equal to 30% then repair is needed, or further use of the item will lead
     * to damage.
     * @return True - if getHealth() is less than or equal to 30%
     *         False - if getHealth() is greater than 30%.
     */
    default boolean needsRepair() {
        return this.getHealth() <= REPAIR_THRESHOLD;
    }

    /**
     * Applies damage to the Damageable object at the appropriate rate.
     */
    void damage();

    /**
     * Return whether the current object is non-functional. An object is non-functional if it's
     * health is less than or equal to 0.
     * @return True - if health is less than or equal to 0.
     *         False - if health is greater than 0.
     */
    default boolean isBroken() {
        return this.getHealth() <= 0;
    }

    /**
     * Recalculates maximum health and resets health to maximum.
     */
    void resetHealth();

    /**
     * Sets the damage rate of a Damageable object to newDamageRate.
     * @param newDamageRate The new damage rate.
     * @requires newDamageRate to be greater than or equal to 0.
     */
    void setDamageRate(int newDamageRate);

}
