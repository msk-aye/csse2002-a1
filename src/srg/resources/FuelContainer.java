package srg.resources;

/**
 * A child of ResourceContainer which stores fuel type resources. Can store either TRITIUM grade
 * fuel or HYPERDRIVE_CORE.
 */
public class FuelContainer extends ResourceContainer {
    /** Maximum capacity for a FuelContainer. Contains 1000 units. */
    public static final int MAXIMUM_CAPACITY = 1000;
    /** The grade od the fuel that is stored in this FuelContainer. */
    private final FuelGrade grade;
    /** The amount of fuel currently stored in this FuelContainer*/
    private int amount;
    /** The type of resource this container is holding (FUEL as it is a FuelContainer).*/
    private final ResourceType type = ResourceType.FUEL;

    /**
     * Constructs a FuelContainer holding an amount of fuel of a specified FuelGrade.
     * @param grade The grade of fuel this FuelContainer will hold, can be either TRITIUM or
     *              HYPERDRIVE_CORE, based on the FuelGrade Enum.
     * @param amount The amount of fuel to be stored on initialisation.
     */
    public FuelContainer(FuelGrade grade, int amount) {
        super(ResourceType.FUEL, amount);
        this.amount = amount;
        this.grade = grade;
    }

    /**
     * Returns the grade of fuel being stored.
     * @return The grade of the fuel being stored.
     */
    public FuelGrade getFuelGrade() {
        return this.grade;
    }

    /**
     * Checks if resources of the specified ResourceType can be stored in this FuelContainer.
     * Overrides canStore in ResourceContainer.
     * @param type The ResourceType to check.
     * @return True - if the ResourceType is ResourceType.FUEL.
     *         False - if the ResourceType is not ResourceType.FUEL.
     */
    @Override
    public boolean canStore(ResourceType type) {
        return type.equals(ResourceType.FUEL);
    }

    /**
     * Returns a String representation of this FuelContainer. Overrides toString in
     * ResourceContainer.
     * @return A string of format: "ResourceType: amount - grade".
     */
    @Override
    public String toString() {
        return String.format("%s: %s - %s", this.type, this.amount, this.grade);
    }

    /**
     * Returns the short name of the type of fuel stored in this FuelContainer
     * @return A String containing the type of this resource, based on the FuelGrade enum
     *         ('TRITIUM' or 'HYPERDRIVE_CORE').
     */
    @Override
    public String getShortName() {
        return this.grade.toString();
    }
}
