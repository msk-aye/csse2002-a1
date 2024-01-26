package srg.resources;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceContainerTest {

    private ResourceContainer resourceContainer;
    @Before
    public void setup() {
        resourceContainer = new ResourceContainer(ResourceType.REPAIR_KIT, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failedConstructor() {
        new ResourceContainer(ResourceType.FUEL, 10);
    }

    @Test
    public void canStoreFuel() {
        assertTrue(resourceContainer.canStore(ResourceType.REPAIR_KIT));
    }

    @Test
    public void canStoreResource() {
        assertFalse(resourceContainer.canStore(ResourceType.FUEL));
    }

    @Test
    public void getAmount() {
        assertEquals(1, resourceContainer.getAmount());
    }

    @Test
    public void setAmount() {
        assertEquals(1, resourceContainer.getAmount());
        resourceContainer.setAmount(10);
        assertEquals(10, resourceContainer.getAmount());
    }

    @Test
    public void setAmountNegative() {
        assertEquals(1, resourceContainer.getAmount());
        resourceContainer.setAmount(-10);
        assertEquals(-10, resourceContainer.getAmount());
    }

    @Test
    public void getType() {
        assertEquals(ResourceType.REPAIR_KIT, resourceContainer.getType());
    }

    @Test
    public void string() {
        assertEquals("REPAIR_KIT: 1", resourceContainer.toString());
    }

    @Test
    public void getShortName() {
        assertEquals("REPAIR_KIT", resourceContainer.getShortName());
    }
}
