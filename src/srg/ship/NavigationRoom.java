package srg.ship;

import srg.exceptions.InsufficientResourcesException;
import srg.exceptions.NoPathException;
import srg.ports.ShipYard;
import srg.ports.SpacePort;
import srg.ports.Store;
import srg.resources.FuelGrade;

import java.util.ArrayList;
import java.util.List;

/**
 * A child class of Room which represents a NavigationRoom in a Ship. NavigationRooms track the
 * Ship's current Port and the galaxy map which lists all SpacePorts in the galaxy.
 * NavigationRooms also track which SpacePorts the Ship could fly to (shorter distance) or jump
 * to (longer distance).
 */
public class NavigationRoom extends Room {
    /** The galaxy map which lists all SpacePorts in the galaxy. */
    public List<SpacePort> galaxyMap;
    /** The index of the galaxyMap (spacePort) that the ship is currently at. */
    private int currentIndex = 0;

    /**
     * Constructor which creates a NavigationRoom at a specified RoomTier. The NavigationRoom's
     * current port should be set to the 0th SpacePort in the galaxyMap.
     * @param roomTier The quality of the NavigationRoom's tier.
     * @param galaxyMap A List of all SpacePorts in the galaxy.
     */
    public NavigationRoom(RoomTier roomTier, List<SpacePort> galaxyMap) {
        super(roomTier);
        this.galaxyMap = galaxyMap;
    }

    /**
     * Returns the current port.
     * @return The SpacePort the ship is currently at.
     */
    public SpacePort getCurrentPort() {
        return galaxyMap.get(currentIndex);
    }

    /**
     * Returns the Ship's maximum flying distance.
     * @return Maximum flying distance as an int. This is determined by the quality of the
     *         NavigationRoom, as defined by the RoomTier enum. BASIC NavigationRooms can fly 200
     *         units. AVERAGE NavigationRooms can fly 400 units. PRIME NavigationRooms can fly
     *         600 units.
     */
    public int getMaximumFlyDistance() {
        // Numbers below represent fly range as mentioned in javaDoc.
        int fly = 200; // Case BASIC
        switch (this.getTier()) {
            case AVERAGE -> fly = 400;
            case PRIME -> fly = 600;
        }
        return fly;
    }

    /**
     * Returns the Ship's maximum jump distance.
     * @return Maximum jump distance as an int. This is determined by the quality of the
     *         NavigationRoom, as defined by the RoomTier enum. BASIC NavigationRooms can jump
     *         500 units. AVERAGE NavigationRooms can jump 750 units. PRIME NavigationRooms can
     *         jump 1000 units.
     */
    public int getMaximumJumpDistance() {
        // Numbers below represent jump range as mentioned in javaDoc.
        int jump = 500; // Case BASIC
        switch (this.getTier()) {
            case AVERAGE -> jump = 750;
            case PRIME -> jump = 1000;
        }
        return jump;
    }

    /**
     * Returns the amount of fuel required to travel to another SpacePort.
     * @param spacePort The SpacePort to travel to.
     * @return The amount of fuel required to travel to spacePort. This is equal to the distance
     *         between the current Port and spacePort.
     */
    public int getFuelNeeded(SpacePort spacePort) {
        return galaxyMap.get(currentIndex).getPosition().distanceTo(spacePort.getPosition());
    }

    /**
     * Returns a List of SpacePorts the Ship could fly to. This list does not include the
     * current SpacePort.
     * @return A List of Spaceports whose distance is less than or equal to the maximum flying
     *         distance.
     */
    public List<SpacePort> getPortsInFlyRange() {
        List<SpacePort> returnList =  new ArrayList<>();
        // To avoid indexing error in case where galaxyMap is null
        if (galaxyMap.size() > 0) {
            SpacePort current = this.getCurrentPort();

            for (SpacePort next : galaxyMap) {
                // Filter out current port
                if (next != current) {

                    // Distance small enough to fly
                    if (current.getPosition().distanceTo(next.getPosition())
                            <= this.getMaximumFlyDistance()) {

                        returnList.add(next);
                    }
                }
            }
        }
        return returnList;
    }

    /**
     * Returns a List of SpacePorts the Ship could jump to.
     * @return A List of Spaceports whose distance is greater than the maximum flying distance,
     *         but less than or equal to the maximum jumping distance.
     */
    public  List<SpacePort> getPortsInJumpRange() {
        List<SpacePort> returnList =  new ArrayList<>();
        // To avoid indexing error in case where galaxyMap is null
        if (galaxyMap.size() > 0) {
            SpacePort current = this.getCurrentPort();

            for (SpacePort next : galaxyMap) {
                // Filter out current port
                if (next != current) {

                    // Distance too large to fly
                    if (current.getPosition().distanceTo(next.getPosition())
                            > this.getMaximumFlyDistance()) {

                        // Distance small enough to jump
                        if (current.getPosition().distanceTo(next.getPosition())
                                <= this.getMaximumJumpDistance()) {

                            returnList.add(next);
                        }
                    }
                }
            }
        }
        return returnList;
    }

    /**
     * Get the list of actions that it is possible to perform from this NavigationRoom. A
     * NavigationRoom is able to fly to SpacePorts in fly range, and jump to SpacePorts in jump
     * range. Overrides getActions in Room.
     * @return A List of actions that this NavigationRoom can perform as Strings.
     *         Format: For flyable SpacePorts: "fly to \"SpacePort name\": SpacePort details
     *                 [COST: amount of fuel needed TRITIUM FUEL]".
     *         For jump-able SpacePorts: "jump to \" SpacePort name\" [COST: 1 HYPERDRIVE CORE]
     */
    @Override
    public List<String> getActions() {
        List<String> returnList = new ArrayList<>();

        // To avoid indexing error in null case
        if (this.getPortsInFlyRange().size() > 0) {
            for (SpacePort spaceport : this.getPortsInFlyRange()) {

                String returnString =
                    String.format("fly to \"%s\": PORT: \"%s\" %s at %s [COST: %s TRITIUM FUEL]",
                        spaceport.getName(),
                        spaceport.getName(),
                        spaceport.getClass().getSimpleName(),
                        spaceport.getPosition().toString(),
                        this.getFuelNeeded(spaceport));

                returnList.add(returnString);
            }
        }

        // To avoid indexing error in null case
        if (this.getPortsInJumpRange().size() > 0) {
            for (SpacePort spaceport : this.getPortsInJumpRange()) {

                String returnString = String.format("jump to \"%s\" [COST: 1 HYPERDRIVE CORE]",
                        spaceport.getName());

                returnList.add(returnString);
            }
        }

        return returnList;
    }

    /**
     * Checks if the current port is a ShipYard
     * @return The current port as a ShipYard, if the current port is a ShipYard; null otherwise.
     */
    public ShipYard getShipYard() {
        if (this.getCurrentPort() instanceof ShipYard) {
            return (ShipYard) this.getCurrentPort();
        }
        return null;
    }

    /**
     * Checks if the current port is a Store.
     * @return The current port as a Store, if the current port is a Store; null otherwise.
     */
    public Store getStore() {
        if (this.getCurrentPort() instanceof Store) {
            return (Store) this.getCurrentPort();
        }
        return null;
    }

    /**
     * Returns a SpacePort based on a specified name.
     * @param name The unique name of the SpacePort.
     * @return The specified SpacePort if it can be found.
     * @throws NoPathException If the named SpacePort cannot be found.
     */
    public SpacePort getSpacePortFromName(String name)
            throws NoPathException {

        for (SpacePort spacePort : this.galaxyMap) {
            if (name.equals(spacePort.getName())) {
                return spacePort;
            }
        }

        throw new NoPathException();
    }

    /**
     * Helper method to get the distance between current port and another port.
     * @param otherPort The other port to measure distance from.
     * @return Integer representation of the distance between the current port and otherPort.
     * @requires otherPort to be a SpacePort in galaxyMap.
     */
    private int getDistanceToPort(SpacePort otherPort) {
        return this.getCurrentPort().getPosition().distanceTo(otherPort.getPosition());
    }

    /**
     * Flies the Ship to the specified SpacePort if it is in range, and the ship is able to
     * support the journey. Ship's Rooms take damage at their damage rate when flying. TRITIUM is
     * used up at a rate of one unit per unit of distance travelled.
     * @param portName A String representation of the target SpacePort's unique name.
     * @param cargoHold The Ship's CargoHold.
     * @throws InsufficientResourcesException If the CargoHold is broken; if the NavigationRoom
     *                                        is broken; or if there is insufficient fuel of
     *                                        the relevant type in the CargoHold.
     * @throws NoPathException If the named SpacePort cannot be found or is out of range.
     */
    public void flyTo(String portName, CargoHold cargoHold)
            throws InsufficientResourcesException, NoPathException {

        if (this.isBroken() || cargoHold.isBroken()) {
            throw new InsufficientResourcesException();
        }

        SpacePort spacePort = this.getSpacePortFromName(portName);

        // The port is out of range
        if (this.getMaximumFlyDistance() < this.getDistanceToPort(spacePort)) {
            throw new NoPathException();
        }

        // Enough fuel to make flight
        if (this.getFuelNeeded(spacePort) <= cargoHold.getTotalAmountByType(FuelGrade.TRITIUM)) {
            cargoHold.consumeResource(FuelGrade.TRITIUM, this.getFuelNeeded(spacePort));
            this.damage();
            cargoHold.damage();
            currentIndex = galaxyMap.indexOf(spacePort);

        // Not enough fuel to make flight
        } else {
            throw new InsufficientResourcesException();
        }
    }

    /**
     * Jumps the Ship to the specified SpacePort if it is in range, and the ship is able to
     * support the journey. Ship's Rooms take damage at their damage rate when jumping.Making a
     * jump requires 1 HYPERDRIVE_CORE.
     * @param portName A String representation of the target SpacePort's unique name.
     * @param cargoHold The Ship's CargoHold.
     * @throws InsufficientResourcesException If the CargoHold is broken; if the NavigationRoom
     *                                        is broken; or if there is insufficient fuel of
     *                                        the relevant type in the CargoHold.
     * @throws NoPathException If the named SpacePort cannot be found or is out of range.
     */
    public void jumpTo(String portName, CargoHold cargoHold)
            throws InsufficientResourcesException, NoPathException {

        if (this.isBroken() || cargoHold.isBroken()) {
            throw new InsufficientResourcesException();
        }

        SpacePort spacePort = this.getSpacePortFromName(portName);

        // The port is out of range
        if (this.getMaximumJumpDistance() < this.getDistanceToPort(spacePort)) {
            throw new NoPathException();
        }

        /* The number of HYPERDRIVE CORES needed to make a jump (1). (To avoid magic numbers). */
        int hyperdriveCoresNeeded = 1;

        // Enough fuel to make jump
        if (hyperdriveCoresNeeded <= cargoHold.getTotalAmountByType(FuelGrade.HYPERDRIVE_CORE)) {
            cargoHold.consumeResource(FuelGrade.HYPERDRIVE_CORE, hyperdriveCoresNeeded);
            this.damage();
            cargoHold.damage();
            currentIndex = galaxyMap.indexOf(spacePort);

        // Not enough fuel to make jump
        } else {
            throw new InsufficientResourcesException();
        }
    }
}
