package garden.ephemeral.dozenal;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class DozenalFormat extends NumberFormat {

    private static final char ZERO = '0';
    private static final char[] DIGITS = "0123456789↊↋".toCharArray();
    private static final int BUFFER_LEN = 20;
    private static final int RADIX = 12;
    private static final int GROUP = RADIX * RADIX * RADIX;
    private static final char GROUP_SEPARATOR = ',';

    private final boolean diagnosticMode;
    private char[] buffer = new char[BUFFER_LEN];

    public DozenalFormat() {
        this(false);
    }

    public DozenalFormat(boolean diagnosticMode) {
        this.diagnosticMode = diagnosticMode;
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        throw new NotYetImplementedException();
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        if (number < 0) {
            throw new NotYetImplementedException();
        }

        pos.setBeginIndex(toAppendTo.length());
        if (diagnosticMode) {
            toAppendTo.append('[');
        }

        int index = BUFFER_LEN;
        while (number > 0) {
            if (index < BUFFER_LEN) {
                index--;
                buffer[index] = GROUP_SEPARATOR;
            }
            int group = (int) (number % GROUP);
            number = number / GROUP;

            index = formatGroup(group, index);
        }

        if (index == BUFFER_LEN) {
            index = formatDigit(0, index);
        } else {
            index = trimLeadingZeroes(index);
        }

        toAppendTo.append(buffer, index, buffer.length - index);

        if (diagnosticMode) {
            toAppendTo.append(']');
        }
        pos.setEndIndex(toAppendTo.length());
        return toAppendTo;
    }

    private int formatGroup(int group, int index) {
        int d3 = group % 12; group /= 12;
        index = formatDigit(d3, index);
        int d2 = group % 12;
        index = formatDigit(d2, index);
        int d1 = group / 12;
        index = formatDigit(d1, index);
        return index;
    }

    private int formatDigit(int digit, int index) {
        index--;
        buffer[index] = DIGITS[digit];
        return index;
    }

    private int trimLeadingZeroes(int index) {
        while (buffer[index] == ZERO) {
            index++;
        }
        return index;
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        int accum = 0;
        int index;

        loop:
        for (index = 0; index < source.length(); index++) {
            char ch = source.charAt(index);
            int digit;
            switch (ch) {
                case '0': digit = 0; break;
                case '1': digit = 1; break;
                case '2': digit = 2; break;
                case '3': digit = 3; break;
                case '4': digit = 4; break;
                case '5': digit = 5; break;
                case '6': digit = 6; break;
                case '7': digit = 7; break;
                case '8': digit = 8; break;
                case '9': digit = 9; break;
                case '↊': digit = 10; break;
                case '↋': digit = 11; break;
                case ',': continue;
                default:
                    break loop;
            }
            accum *= RADIX;
            accum += digit;
        }
        parsePosition.setIndex(index);
        return accum;
    }
}
