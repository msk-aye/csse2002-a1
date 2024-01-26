package srg.ports;

import srg.exceptions.InsufficientCapcaityException;
import srg.exceptions.InsufficientResourcesException;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;
import srg.ship.CargoHold;
import srg.ship.RoomTier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A child class of SpacePort with a store, where Ships can restock on resources.
 */
public class Store extends SpacePort {
    /**
     * The CargoHold for the Store. The CargoHold is where the Store stores its
     * ResourceContainers.
     */
    private CargoHold cargoHold;

    /**
     * Construct a Store containing a CargoHold, which can sell items to ships. One container
     * with the maximum store-able amount is added to the CargoHold, for each ResourceType and
     * each FuelGrade. The CargoHold has an RoomTier.AVERAGE tier.
     * @param name The unique name of the Store.
     * @param position The Position of the Store.
     */
    public Store(String name, Position position) {
        super(name, position);

        this.cargoHold = new CargoHold(RoomTier.AVERAGE);
        /*  Try catch needs to be used as cargoHold.storeResource can theoretically throw
            InsufficientCapcaityException (but it will never throw it as the .storeResource method
            is being called sensibly).
         */
        try {
            cargoHold.storeResource(new ResourceContainer(ResourceType.REPAIR_KIT,
                    ResourceContainer.MAXIMUM_CAPACITY));
            cargoHold.storeResource(new FuelContainer(FuelGrade.TRITIUM,
                    FuelContainer.MAXIMUM_CAPACITY));
            cargoHold.storeResource(new FuelContainer(FuelGrade.HYPERDRIVE_CORE,
                    FuelContainer.MAXIMUM_CAPACITY));
        } catch (InsufficientCapcaityException e) {
            return; // This line will never be reached
        }
    }

    /**
     * Remove an item from the store, and return a resource container containing the removed
     * amount of the same item.
     * @param item The short string representation of the item name.
     * @param amount The amount of the resource to purchase.
     * @return A ResourceContainer containing the amount of resource corresponding to the item name.
     * @throws InsufficientResourcesException if there is not enough resource available in this
     *                                        Store.
     */
    public ResourceContainer purchase(String item, int amount)
            throws InsufficientResourcesException {

        // Case TRITIUM
        if (Objects.equals(item, FuelGrade.TRITIUM.name())) {
            if (this.cargoHold.getTotalAmountByType(ResourceType.FUEL) < amount) {
                throw new InsufficientResourcesException();
            }

            cargoHold.consumeResource(FuelGrade.TRITIUM, amount);
            return new FuelContainer(FuelGrade.TRITIUM, amount);

        // Case REPAIR_KIT
        } else if (Objects.equals(item, ResourceType.REPAIR_KIT.name())) {
            if (this.cargoHold.getTotalAmountByType(ResourceType.REPAIR_KIT) < amount) {
                throw new InsufficientResourcesException();
            }

            cargoHold.consumeResource(ResourceType.REPAIR_KIT, amount);
            return new ResourceContainer(ResourceType.REPAIR_KIT, amount);

        // Case HYPERDRIVE_CORE
        } else if (Objects.equals(item, FuelGrade.HYPERDRIVE_CORE.name())) {
            if (this.cargoHold.getTotalAmountByType(FuelGrade.HYPERDRIVE_CORE) < amount) {
                throw new InsufficientResourcesException();
            }

            cargoHold.consumeResource(FuelGrade.HYPERDRIVE_CORE, amount);
            return new FuelContainer(FuelGrade.HYPERDRIVE_CORE, amount);

        } else {
            throw new InsufficientResourcesException("The specified resource does not exist.");
        }
    }

    /**
     * Get the list of actions that it is possible to perform at this SpacePort.Stores sell items
     * that appear in their CargoHold. Action Strings are of format:
     * "buy item name 1..maximum number available". Overrides getActions in SpacePort.
     * @return A List of actions that are unique to this SpacePort as Strings.
     */
    public List<String> getActions() {
        List<String> returnList = new ArrayList<>();

        for (ResourceContainer container : cargoHold.getResources()) {
            // Add any REPAIR_KITS to the list
            if (container.getShortName().equals("REPAIR_KIT") && container.getAmount() > 0) {
                returnList.add("buy REPAIR_KIT 1.."
                        + cargoHold.getTotalAmountByType(ResourceType.REPAIR_KIT));
            }

            // Add any HYPERDRIVE_CORE to the list
            if (container.getShortName().equals("HYPERDRIVE_CORE") && container.getAmount() > 0) {
                returnList.add("buy HYPERDRIVE_CORE 1.."
                        + cargoHold.getTotalAmountByType(FuelGrade.HYPERDRIVE_CORE));
            }

            // Add any TRITIUM to the list
            if (container.getShortName().equals("TRITIUM") && container.getAmount() > 0) {
                returnList.add("buy TRITIUM 1.."
                        + cargoHold.getTotalAmountByType(FuelGrade.TRITIUM));
            }
        }

        return returnList;
    }
}
