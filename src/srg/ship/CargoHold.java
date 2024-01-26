package srg.ship;

import srg.exceptions.InsufficientCapcaityException;
import srg.exceptions.InsufficientResourcesException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * A child class of Room which is able to store ResourceContainers.
 */
public class CargoHold extends Room {
    /** The capacity of this CargoHold, depends on RoomTier*/
    private int capacity;
    /** A list of ResourceContainers stored by this CargoHold. */
    private ArrayList<ResourceContainer> resources = new ArrayList<>();

    /**
     * Constructor which creates a CargoHold at a specified RoomTier.
     * @param roomTier The quality of the CargoHold's tier.
     */
    public CargoHold(RoomTier roomTier) {
        super(roomTier);
        switch (roomTier) {
            case BASIC -> this.capacity = 5;
            case AVERAGE -> this.capacity = 10;
            case PRIME -> this.capacity = 15;
        }
    }

    /**
     * Returns the remaining capacity of the CargoHold for new ResourceContainers.
     * @return The maximum capacity of the CargoHold less any ResourceContainers currently stored.
     */
    public int getRemainingCapacity() {
        return this.capacity - this.resources.size();
    }

    /**
     * Returns the maximum capacity of the CargoHold based on its tier.
     * @return An integer representing the CargoHold's maximum capacity.
     */
    public int getMaximumCapacity() {
        return this.capacity;
    }

    /**
     * Returns the list of ResourceContainers stored by this CargoHold.
     * @return A list of ResourceContainers stored by this CargoHold, ordered from oldest to newest.
     */
    public List<ResourceContainer> getResources() {
        return this.resources;
    }

    /**
     * Attempts to add a new ResourceContainer to this CargoHold.
     * @param resource The ResourceContainer to add to this CargoHold
     * @throws InsufficientCapcaityException  If there is not enough capacity to add resource,
     *                                        i.e. CargoHold already stores the maximum capacity
     *                                        or higher.
     */
    public void storeResource(ResourceContainer resource)
            throws InsufficientCapcaityException {
        if (this.getRemainingCapacity() == 0) {
            throw new InsufficientCapcaityException();
        } else {
            this.resources.add(resource);
        }
    }

    /**
     * Returns a list of ResourceContainers which store given ResourceType.
     * @param type The ResourceType to enumerate.
     * @return A list of ResourceContainers holding resources of a particular type.
     */
    public List<ResourceContainer> getResourceByType(ResourceType type) {
        List<ResourceContainer> returnList = new ArrayList<>();

        for (ResourceContainer resource : this.resources) {
            if (type.equals(resource.getType())) {
                returnList.add(resource);
            }
        }

        return returnList;
    }

    /**
     * Return a list of ResourceContainers holding fuel of a particular FuelGrade.
     * @param grade The FuelGrade to enumerate.
     * @return A list of ResourceContainers holding fuel of a particular FuelGrade.
     */
    public List<ResourceContainer> getResourceByType(FuelGrade grade) {
        List<ResourceContainer> returnList = new ArrayList<>();

        for (ResourceContainer resource : this.resources) {
            if (resource instanceof FuelContainer) {
                if (grade.equals(((FuelContainer) resource).getFuelGrade())) {
                    returnList.add(resource);
                }
            }
        }

        return returnList;
    }

    /**
     * Sums the quantity of a given resource across the ResourceContainers.
     * @param type The ResourceType to sum.
     * @return The total quantity of the resource.
     */
    public int getTotalAmountByType(ResourceType type) {
        int amount = 0;

        for (ResourceContainer resource : this.resources) {
            if (type.equals(resource.getType())) {
                amount += resource.getAmount();
            }
        }

        return amount;
    }

    /**
     * Sums the quantity of a given resource across the ResourceContainers.
     * @param grade The FuelGrade to sum.
     * @return The total quantity of the fuel grade.
     */
    public int getTotalAmountByType(FuelGrade grade) {
        int amount = 0;

        for (ResourceContainer resource : this.resources) {
            if (resource instanceof FuelContainer) {
                if (grade.equals(((FuelContainer) resource).getFuelGrade())) {
                    amount += resource.getAmount();
                }
            }
        }

        return amount;
    }

    /**
     * Consumes the specified amount of non-fuel resources. Resources are consumed from
     * ResourceContainers in the order that the container was stored. Once a container is emptied
     * of content, it is removed from the CargoHold.
     * @param type The resource type to be consumed.
     * @param amount The amount to be consumed.
     * @requires amount to be greater than 0.
     * @throws InsufficientResourcesException If amount is greater than the total amount of the
     *                                        resource in CargoHold.
     */
    public void consumeResource(ResourceType type, int amount)
            throws InsufficientResourcesException {

        // Amount exceeds the total amount
        if (amount > this.getTotalAmountByType(type)) {
            throw new InsufficientResourcesException();
        }

        if (type == ResourceType.FUEL) {
            throw new IllegalArgumentException();
        }

        for (ResourceContainer container : resources) {
            if (container.getType() == type) {

                // If the container has enough, simply subtract from the container and return
                if (container.getAmount() >= amount) {
                    container.setAmount(container.getAmount() - amount);
                    break;

                // If container doesn't have enough, use container and update the amount needed
                } else {
                    amount -= container.getAmount();
                    resources.remove(container);
                }
            }
        }
    }

    /**
     * Consumes the specified amount of fuel resources. Fuel resources are consumed from
     * ResourceContainers in the order that the container was stored. Once a container is emptied
     * of content, it is removed from the CargoHold.
     * @param grade The fuel grade to be consumed.
     * @param amount The amount to be consumed.
     * @requires amount to be greater than 0.
     * @throws InsufficientResourcesException If amount is greater than the total amount of the
     *                                        resource in CargoHold.
     */
    public void consumeResource(FuelGrade grade, int amount)
            throws InsufficientResourcesException {

        // Amount exceeds the total amount
        if (amount > this.getTotalAmountByType(grade)) {
            throw new InsufficientResourcesException();
        }

        for (ResourceContainer container : resources) {
            if (container.getType() == ResourceType.FUEL) {

                if (((FuelContainer) container).getFuelGrade() == grade) {

                    // If the container has enough, simply subtract from the container and return
                    if (container.getAmount() >= amount) {
                        container.setAmount(container.getAmount() - amount);
                        break;

                    // If container doesn't have enough, use container and update the amount needed
                    } else {
                        amount -= container.getAmount();
                        resources.remove(container);
                    }
                }
            }
        }
    }

    /**
     * Returns a string representation of a CargoHold.
     * @return A string representing all the details of the CargoHold.
     */
    public String toString() {
        String returnString =
            String.format("ROOM: %s(%s) health: %s%%, needs repair: %s, capacity: %s, items: %s",
                this.getClass().getSimpleName(),
                this.getTier(),
                this.getHealth(), this.needsRepair(),
                this.getMaximumCapacity(),
                this.resources.size());

        // If there are REPAIR_KITS, then add the amount to the string
        if (this.getResourceByType(ResourceType.REPAIR_KIT).size() > 0) {
            returnString += String.format("\n    REPAIR_KIT: %s",
                    this.getTotalAmountByType(ResourceType.REPAIR_KIT));
        }

        // If there is TRITIUM, then add the amount to the string
        if (this.getResourceByType(FuelGrade.TRITIUM).size() > 0) {
            returnString += String.format("\n    FUEL: %s - TRITIUM",
                    this.getTotalAmountByType(FuelGrade.TRITIUM));
        }

        // If there are HYPERDRIVE_CORES, then add the amount to the string
        if (this.getResourceByType(FuelGrade.HYPERDRIVE_CORE).size() > 0) {
            returnString += String.format("\n    FUEL: %s - HYPERDRIVE_CORE",
                    this.getTotalAmountByType(FuelGrade.HYPERDRIVE_CORE));
        }
        return returnString;
    }

    /**
     * Get the list of actions that it is possible to perform from this CargoHold. A CargoHold is
     * able to repair Rooms in the Ship if it has any REPAIR_KITs available. If the CargoHold can
     * repair, there must be an action for both types of rooms, namely: CargoHold and
     * NavigationRoom. Overrides getActions in Room.
     * @return  List of actions that this CargoHold can perform as Strings.
     *          Format: "repair Room [COST: 1 REPAIR_KIT]"
     */
    @Override
    public List<String> getActions() {
        List<String> returnList = new ArrayList<>();
        if (this.getResourceByType(ResourceType.REPAIR_KIT).size() > 0) {
            returnList.add("repair NavigationRoom [COST: 1 REPAIR_KIT]");
            returnList.add("repair CargoHold [COST: 1 REPAIR_KIT]");
        }
        return returnList;
    }

}
