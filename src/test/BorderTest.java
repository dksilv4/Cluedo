

import code.Border;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BorderTest {
    Border border = new Border(1, 1);

    @Before
    public void setUp() {
    }

    @Test
    public void testToString() {
        assertEquals("\u001B[33mb\u001B[0m", this.border.toString());
    }

    @Test
    public void testGetRow() {
        assertEquals(1, this.border.getRow());
    }

    @Test
    public void testGetColumn() {
        assertEquals(1, this.border.getColumn());
    }

    @Test
    public void testGetType() {
        assertEquals("border", this.border.getType());
    }
}
