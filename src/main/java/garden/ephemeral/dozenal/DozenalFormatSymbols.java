package garden.ephemeral.dozenal;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DozenalFormatSymbols extends DecimalFormatSymbols {
    public static final DozenalFormatSymbols INSTANCE = new DozenalFormatSymbols();

    private DozenalFormatSymbols() {
        super(Locale.ENGLISH);

        setDecimalSeparator(';');
    }
}
