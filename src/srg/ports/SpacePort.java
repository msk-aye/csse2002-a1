package srg.ports;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a generic SpacePort. Implementation is through subclasses ShipYard and Store.
 */
public class SpacePort {
    /** The unique name of the SpacePort. */
    private final String name;
    /** The physical Position of the SpacePort. */
    private final Position position;

    /**
     * Constructs a SpacePort with given name and Position.
     * @param name The unique name of this SpacePort.
     * @param position The physical Position of this SpacePort.
     * @requires name to be unique.
     */
    public SpacePort(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    /**
     * Returns the name of this SpacePort.
     * @return The name of this SpacePort.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the Position of this SpacePort.
     * @return The Position of this SpacePort.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Returns a string representation of a SpacePort. Identifies the name, type of SpacePort,
     * and position.
     * @return A string containing the name, type, and position of the SpacePort.
     *         Format: "PORT: \"SpacePort name\" SpacePort class name at position".
     */
    public String toString() {
        return String.format("PORT: \"%s\" %s at %s", this.name,
                this.getClass().getSimpleName(),
                this.position);
    }

    /**
     * Get the list of actions that it is possible to perform at this SpacePort. Generic
     * SpacePorts have no actions.
     * @return A list of actions for this SpacePort as strings. (Empty list for generic SpacePorts).
     */
    public List<String> getActions() {
        return new ArrayList<>();
    }
}
