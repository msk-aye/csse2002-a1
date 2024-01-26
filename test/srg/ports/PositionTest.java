package srg.ports;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PositionTest {

    private Position position;

    @Before
    public void setup() {
        position = new Position(0, 0, 0);
    }

    @Test
    public void distanceToSelf() {
        assertEquals(0, position.distanceTo(position));
    }

    @Test
    public void distanceToDestination() {
        Position destination = new Position(4,5,6);
        assertEquals(8, position.distanceTo(destination));
        assertEquals(position.distanceTo(destination), destination.distanceTo(position));
    }

    @Test
    public void distanceToNegativeDestination() {
        Position destination = new Position(-3, -4,-5);
        assertEquals(7, position.distanceTo(destination));
        assertEquals(position.distanceTo(destination), destination.distanceTo(position));
    }

    @Test
    public void distanceToFarDestination() {
        Position destination = new Position(100, 300,600);
        assertEquals(678, position.distanceTo(destination));
        assertEquals(position.distanceTo(destination), destination.distanceTo(position));
    }

    @Test
    public void string() {
        assertEquals("(0, 0, 0)", position.toString());
    }
}
