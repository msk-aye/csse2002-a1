package srg.ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Room in a Ship. Rooms are Damageable objects. Rooms also have a tier which
 * contributes to determining starting health and damage rate.
 */
public class Room implements Damageable {
    /**
     * The rate at which damage occurs to the room, depending on both DAMAGE_RATE in Damageable
     * interface and the rooms tier.
     */
    private int damageRate;
    /** The current health the room has left. */
    private int health;
    /**
     * The maximum health of the room, depends on both MAX_HEALTH in Damageable and the rooms
     * tier
     */
    private int maxHealth;
    /** The tier of the room, based on the RoomTier Enum. */
    private RoomTier tier;

    /**
     * Constructs a Room and assigns it the given tier. The room has starting values for each of
     * the member variables health, maxHealth and damageRate.
     * @param tier The tier of the room, based on the RoomTier Enum (can be BASIC, AVERAGE or
     *             PRIME).
     */
    public Room(RoomTier tier) {
        this.damageRate = DAMAGE_RATE * tier.damageMultiplier;
        this.maxHealth = HEALTH_MULTIPLIER * tier.healthMultiplier;
        this.health = this.maxHealth;
        this.tier = tier;
    }

    /**
     * Constructs a Room and automatically assigns it a BASIC tier.
     */
    public Room() {
        this(RoomTier.BASIC);
    }

    /**
     * Upgrades a Room based on its current tier, resets its health to the new maximum, and sets
     * the new damage rate. If the room is already PRIME, it resets health to maximum.
     */
    public void upgrade() {
        // Assign new tier
        switch (this.tier) {
            case BASIC -> this.tier = RoomTier.AVERAGE;
            case AVERAGE -> this.tier = RoomTier.PRIME;
        }
        // Recalculate the stats
        this.setDamageRate(DAMAGE_RATE * tier.damageMultiplier);
        this.resetHealth();
    }

    /**
     * Sets the damage rate of a Damageable object to newDamageRate.
     * @param newDamageRate The new damage rate.
     * @requires newDamageRate to be greater than or equal to 0.
     */
    public void setDamageRate(int newDamageRate) {
        this.damageRate = newDamageRate;
    }

    /**
     * Returns the health as an integer percentage of the maximum. Health may become negative, in
     * which case the percentage should also be negative.
     * @return True - if getHealth() is less than or equal to 30%
     *         False - if getHealth() is greater than 30%.
     */
    public int getHealth() {
        return ((int) (this.health * 100 / this.maxHealth));
    }

    /**
     * Applies damage to the Damageable object at the appropriate rate.
     */
    public void damage() {
        this.health -= this.damageRate;
    }

    /**
     * Recalculates maximum health and resets health to maximum.
     */
    public void resetHealth() {
        this.maxHealth = HEALTH_MULTIPLIER * tier.healthMultiplier;
        this.health = this.maxHealth;
    }

    /**
     * Gets the status of the Room's tier.
     * @return The room's RoomTier.
     */
    public RoomTier getTier() {
        return this.tier;
    }

    /**
     * Returns a String representation of a Room.
     * @return A String of the format "ROOM: room name(room tier) health: health%,
     *         needs repair: boolean"
     */
    public String toString() {
        return String.format("ROOM: %s(%s) health: %d%%, needs repair: %s",
                this.getClass().getSimpleName(), this.tier, this.getHealth(), this.needsRepair());
    }

    /**
     * Get the list of actions that it is possible to perform from this Room. Generic Rooms have
     * no actions.
     * @return A list of actions for this room as strings.
     */
    public List<String> getActions() {
        return new ArrayList<>();
    }
}