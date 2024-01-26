package srg.ports;

/**
 * Class that represents a three-dimensional position in space.
 */
@SuppressWarnings("ALL")
public class Position {
    /** Position x-coordinate. */
    public final int x;
    /** Position y-coordinate. */
    public final int y;
    /** Position z-coordinate. */
    public final int z;

    /**
     * Constructs a 3d position.
     * @param x Position x-coordinate.
     * @param y Position y-coordinate.
     * @param z Position z-coordinate.
     */
    public Position(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculates the distance between this point and another point.
     * @param other The position to calculate the distance from.
     * @return An integer representation of the distance between the two points, rounded down.
     */
    public int distanceTo(Position other) {
        int distanceX = other.x - this.x;
        int distanceY = other.y - this.y;
        int distanceZ = other.z - this.z;

        return (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY
                + distanceZ * distanceZ);
    }

    /**
     * Creates and returns a formatted string representation of the Position.
     * @return A formatted string representation of the Position.
     *         Format is of "(x-coordinate, y-coordinate, z-coordinate)"
     */
    public String toString() {
        return String.format("(%d, %d, %d)", x, y, z);
    }
}
