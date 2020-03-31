package garden.ephemeral.dozenal;

public class Dozenal {
    public static final int RADIX = 12;
    public static final int PENULTIMATE = RADIX - 1;

    public static final int RADIX_SQUARED = RADIX * RADIX;
    public static final int RADIX_SQUARED_SUB1 = RADIX_SQUARED - 1;

    public static final int RADIX_CUBED = RADIX_SQUARED * RADIX;
    public static final int RADIX_CUBED_SUB1 = RADIX_CUBED - 1;

    public static final int RADIX_TO_4 = RADIX_CUBED * RADIX;
    public static final int RADIX_TO_5 = RADIX_TO_4 * RADIX;

    public static final char DIGIT_ZERO = '0';
    public static final char DIGIT_ONE = '1';
    public static final char DIGIT_PENULTIMATE = '↋';

    public static final String DIGITS = "0123456789↊↋";

    public static char getDigit(int value) {
        return DIGITS.charAt(value);
    }

    public static int getDigitValue(char ch) {
        switch (ch) {
            case '0': return 0;
            case '1': return 1;
            case '2': return 2;
            case '3': return 3;
            case '4': return 4;
            case '5': return 5;
            case '6': return 6;
            case '7': return 7;
            case '8': return 8;
            case '9': return 9;
            case '↊': return 10;
            case '↋': return 11;
            default: return -1;
        }
    }

    public static char nextDigitAfter(char ch) {
        switch (ch) {
            case '0': return '1';
            case '1': return '2';
            case '2': return '3';
            case '3': return '4';
            case '4': return '5';
            case '5': return '6';
            case '6': return '7';
            case '7': return '8';
            case '8': return '9';
            case '9': return '↊';
            case '↊': return '↋';
            case '↋': throw new IllegalArgumentException("Should have checked the digit first!");
            default: throw new IllegalArgumentException("Not a valid digit: " + ch);
        }
    }

    public static long parseLong(String s) throws NumberFormatException {
        if (s == null) {
            throw new NumberFormatException("null");
        }

        long result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        long limit = -Long.MAX_VALUE;
        long multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < DIGIT_ZERO) { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else if (firstChar != '+')
                    throw new NumberFormatException("For input string: \"" + s + "\"");

                if (len == 1) // Cannot have lone "+" or "-"
                    throw new NumberFormatException("For input string: \"" + s + "\"");
                i++;
            }
            multmin = limit / RADIX;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = getDigitValue(s.charAt(i++));
                if (digit < 0) {
                    throw new NumberFormatException("For input string: \"" + s + "\"");
                }
                if (result < multmin) {
                    throw new NumberFormatException("For input string: \"" + s + "\"");
                }
                result *= RADIX;
                if (result < limit + digit) {
                    throw new NumberFormatException("For input string: \"" + s + "\"");
                }
                result -= digit;
            }
        } else {
            throw new NumberFormatException("For input string: \"" + s + "\"");
        }
        return negative ? result : -result;
    }

}
