package garden.ephemeral.dozenal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DozenalFormatTest {

    @Test
    void formatLong() {
        DozenalFormat format = new DozenalFormat();
        assertEquals("0", format.format(0));
        assertEquals("3", format.format(3));
        assertEquals("36", format.format(42));
        assertEquals("100", format.format(144));
        assertEquals("1,000", format.format(1728));
        assertEquals("1,000,000", format.format(2985984));
    }

    @Test
    void formatDouble() {
        DozenalFormat format = new DozenalFormat();
        format.setMinimumFractionDigits(1);
        format.setMaximumFractionDigits(3);
        assertEquals("0;001", format.format(1.0/1728));
        assertEquals("0;01", format.format(1.0/144));
        assertEquals("0;1", format.format(1.0/12));
        assertEquals("1;0", format.format(1.0));
        assertEquals("10;0", format.format(12.0));
        assertEquals("100;0", format.format(144.0));
    }

    @Test
    void formatDoublePercent() {
        DozenalFormat format = new DozenalFormat();
        format.setMinimumFractionDigits(1);
        format.setMaximumFractionDigits(3);
        format.setMultiplier(144);
        format.setPositiveSuffix("%");
        format.setNegativeSuffix("%");
        assertEquals("100;0%", format.format(1.0));
        assertEquals("1;0%", format.format(1.0/144));
    }

    @Test
    void parse() throws Exception {
        DozenalFormat format = new DozenalFormat();

        assertEquals(0L, format.parse("0"));
        assertEquals(1L, format.parse("1"));
        assertEquals(2L, format.parse("2"));
        assertEquals(3L, format.parse("3"));
        assertEquals(4L, format.parse("4"));
        assertEquals(5L, format.parse("5"));
        assertEquals(6L, format.parse("6"));
        assertEquals(7L, format.parse("7"));
        assertEquals(8L, format.parse("8"));
        assertEquals(9L, format.parse("9"));
        assertEquals(10L, format.parse("↊"));
        assertEquals(11L, format.parse("↋"));
        assertEquals(12L, format.parse("10"));

        assertEquals(-3L, format.parse("-3"));

        assertEquals(42L, format.parse("36"));
        assertEquals(144L, format.parse("100"));
        assertEquals(1728L, format.parse("1000"));
        assertEquals(1728L, format.parse("1,000"));
        assertEquals(2985984L, format.parse("1000000"));
        assertEquals(2985984L, format.parse("1,000,000"));
    }
}
