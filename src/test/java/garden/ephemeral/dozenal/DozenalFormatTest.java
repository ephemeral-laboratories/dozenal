package garden.ephemeral.dozenal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DozenalFormatTest {

    @Test
    void format() {
        DozenalFormat format = new DozenalFormat(true);
        assertEquals("[0]", format.format(0));
        assertEquals("[3]", format.format(3));
        assertEquals("[36]", format.format(42));
        assertEquals("[100]", format.format(144));
        assertEquals("[1,000]", format.format(1728));
        assertEquals("[1,000,000]", format.format(2985984));
    }

    @Test
    void parse() throws Exception {
        DozenalFormat format = new DozenalFormat(true);
        assertEquals(0, format.parse("0"));
        assertEquals(3, format.parse("3"));
        assertEquals(42, format.parse("36"));
        assertEquals(144, format.parse("100"));
        assertEquals(1728, format.parse("1000"));
        assertEquals(1728, format.parse("1,000"));
        assertEquals(2985984, format.parse("1000000"));
        assertEquals(2985984, format.parse("1,000,000"));
    }
}
