package srg.resources;

/**
 * A container which can store a certain type of resource (REPAIR_KIT or FUEL).
 * Has a subclass to implement the storage of FUEL.
 */
public class ResourceContainer {
    /** Maximum capacity for a ResourceContainer, how many resources it can hold. */
    public static final int MAXIMUM_CAPACITY = 10;
    /** The type of resource this container will hold. */
    private final ResourceType type;
    /** The amount of resources the container currently holds. */
    private int amount;

    /**
     * Constructs a container filled with a consumable resource of a specified ResourceType.
     * @param type The ResourceType this container will hold.
     * @param amount The amount of resource currently in the container.
     * @throws IllegalArgumentException If the type of resource cannot be stored based on the
     *         canStore method.
     */
    public ResourceContainer(ResourceType type, int amount) throws IllegalArgumentException {
        if (this.canStore(type)) {
            this.type = type;
            this.amount = amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks if this ResourceContainer can store resources of a certain type.
     * @param type The ResourceType to check.
     * @return True - if the ResourceType is not ResourceType.FUEL.
     *         False - if the ResourceType is ResourceType.FUEL.
     */
    public boolean canStore(ResourceType type) {
        return !type.equals(ResourceType.FUEL);
    }

    /**
     * Returns the amount of resource stored in this ResourceContainer.
     * @return The amount of resource currently stored in this ResourceContainer.
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Sets the amount of resource stored in this ResourceContainer.
     * @param amount The new amount of resource.
     * @requires The amount must be at least 1 and less than or equal to MAXIMUM_CAPACITY.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Returns the amount of resource stored in this ResourceContainer.
     * @return The amount of resource stored in this ResourceContainer.
     */
    public ResourceType getType() {
        return type;
    }

    /**
     * Returns a string representation of this ResourceContainer.
     * @return A string of format: "ResourceType: amount".
     */
    public String toString() {
        return String.format("%s: %s", this.type, this.amount);
    }

    /**
     * Returns the short name of the type of resource stored in this ResourceContainer.
     * @return A string containing the type of this resource, based on the ResourceType enum
     *         ('FUEL' or 'REPAIR_KIT').
     */
    public String getShortName() {
        return this.type.toString();
    }
}
