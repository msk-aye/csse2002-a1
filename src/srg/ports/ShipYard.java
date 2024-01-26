package srg.ports;

import srg.ship.Room;

import java.util.*;

/**
 * A child class of SpacePort which is capable of upgrading Rooms in Ships.
 */
public class ShipYard extends SpacePort {
    /** A List of Room classes as class names that can be upgraded by this ShipYard. */
    private List<String> canUpgrade;

    /**
     * Constructs a ShipYard with given name, Position and upgradeable rooms.
     * @param name The unique name of this ShipYard.
     * @param position The Position of this ShipYard.
     * @param canUpgrade A list of Room classes as class names that can be upgraded by this
     *                   ShipYard.
     * @requires name to be unique.
     */
    public ShipYard(String name, Position position, List<String> canUpgrade) {
        super(name, position);
        this.canUpgrade = canUpgrade;
    }

    /**
     * Upgrades a Room. Rooms are upgraded according to their RoomTier.
     * @param room The Room to upgrade.
     * @throws IllegalArgumentException if the class of the room is not on the list of room class
     *                                  names that this ShipYard can upgrade.
     */
    public void upgrade(Room room) {
        if (canUpgrade.contains(room.getClass().getSimpleName())) {
            room.upgrade();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the list of actions that it is possible to perform at this SpacePort. ShipYards are
     * able to upgrade rooms that appear in their canUpgrade list. Action strings are
     * formatted as "upgrade Room class name". Overrides getActions in SpacePort.
     * @return A list of actions for this SpacePort as strings.
     */
    @Override
    public List<String> getActions() {
        List<String> upgrade = new ArrayList<>();
        Collections.reverse(canUpgrade);

        for (String name : this.canUpgrade) {
            String add = String.format("upgrade %s", name);
            upgrade.add(add);
        }

        return upgrade;
    }
}
