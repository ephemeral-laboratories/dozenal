package garden.ephemeral.dozenal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DozenalFormatTest {
    private static final Locale locale = new Locale("en", "US", "DOZ");

    @ParameterizedTest
    @MethodSource("longExamples")
    void formatLong(String expected, long input) {
        NumberFormat format = new DozenalNumberFormatProvider().getIntegerInstance(locale);
        assertEquals(expected, format.format(input));
    }

    @ParameterizedTest
    @MethodSource({"longExamples", "longParseExamples"})
    void parseLong(String input, long expected) throws Exception {
        NumberFormat format = new DozenalNumberFormatProvider().getIntegerInstance(locale);
        assertEquals(expected, format.parse(input));
    }

    static Stream<Arguments> longExamples() {
        return Stream.of(
                arguments("0", 0L),
                arguments("1", 1L),
                arguments("2", 2L),
                arguments("3", 3L),
                arguments("4", 4L),
                arguments("5", 5L),
                arguments("6", 6L),
                arguments("7", 7L),
                arguments("8", 8L),
                arguments("9", 9L),
                arguments("↊", 10L),
                arguments("↋", 11L),
                arguments("10", 12L),

                arguments("-3", -3L),

                arguments("36", 42L),
                arguments("100", 144L),
                arguments("1,000", 1728L),
                arguments("1,000,000", 2985984L)
        );
    }

    static Stream<Arguments> longParseExamples() {
        return Stream.of(
                arguments("1000", 1728L),
                arguments("1000000", 2985984L)
        );
    }

    @ParameterizedTest
    @MethodSource("doubleExamples")
    void formatDouble(String expected, double input) {
        NumberFormat format = new DozenalNumberFormatProvider().getNumberInstance(locale);
        format.setMinimumFractionDigits(1);
        format.setMaximumFractionDigits(3);
        assertEquals(expected, format.format(input));
    }

    static Stream<Arguments> doubleExamples() {
        return Stream.of(
                arguments("0;001", 1.0/1728),
                arguments("0;01", 1.0/144),
                arguments("0;1", 1.0/12),
                arguments("1;0", 1.0),
                arguments("10;0", 12.0),
                arguments("100;0", 144.0)
        );
    }

    @ParameterizedTest
    @MethodSource("formatDoublePercentExamples")
    void formatDoublePercent(double input, String expected) {
        NumberFormat format = new DozenalNumberFormatProvider().getPercentInstance(locale);
        assertEquals(expected, format.format(input));
    }

    static Stream<Arguments> formatDoublePercentExamples() {
        return Stream.of(
                arguments(0.0, "0;0%"),
                arguments(0.84, "↊0;↋%"),
                arguments(1.0, "100;0%"),
                arguments(1.0/144, "1;0%")
        );
    }
}
