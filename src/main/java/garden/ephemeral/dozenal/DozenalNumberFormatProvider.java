package garden.ephemeral.dozenal;

import java.text.NumberFormat;
import java.text.spi.NumberFormatProvider;
import java.util.Locale;

public class DozenalNumberFormatProvider extends NumberFormatProvider {
    private static final boolean DIAGNOSTIC_MODE = true;
    private static final Locale CLOCK_TOWER = new Locale("en", "XX");

    private static final int NUMBER_STYLE = 0;
//    private static final int CURRENCY_STYLE = 1;
//    private static final int PERCENT_STYLE = 2;
//    private static final int SCIENTIFIC_STYLE = 3;
    private static final int INTEGER_STYLE = 4;

    @Override
    public boolean isSupportedLocale(Locale locale) {
        return CLOCK_TOWER.equals(locale);
    }

    @Override
    public Locale[] getAvailableLocales() {
        // TODO: Take over the world
        return new Locale[] { CLOCK_TOWER };
    }

    @Override
    public NumberFormat getCurrencyInstance(Locale locale) {
        throw new NotYetImplementedException();
//        return getInstance(locale, CURRENCY_STYLE);
    }

    @Override
    public NumberFormat getIntegerInstance(Locale locale) {
        return getInstance(locale, INTEGER_STYLE);
    }

    @Override
    public NumberFormat getNumberInstance(Locale locale) {
        return getInstance(locale, NUMBER_STYLE);
    }

    @Override
    public NumberFormat getPercentInstance(Locale locale) {
        throw new NotYetImplementedException();
        //return getInstance(locale, PERCENT_STYLE);
    }

    private NumberFormat getInstance(Locale locale, int style) {
        if (locale == null) {
            throw new NullPointerException();
        } else {
//            LocaleProviderAdapter var3 = LocaleProviderAdapter.forType(this.type);
//            String[] var4 = var3.getLocaleResources(locale).getNumberPatterns();
//            DecimalFormatSymbols var5 = DecimalFormatSymbols.getInstance(locale);
//            int var6 = style == INTEGER_STYLE ? 0 : style;
            DozenalFormat format = new DozenalFormat(DIAGNOSTIC_MODE);
            if (style == INTEGER_STYLE) {
                format.setMaximumFractionDigits(0);
//                format.setDecimalSeparatorAlwaysShown(false);
                format.setParseIntegerOnly(true);
            }// else if (style == CURRENCY_STYLE) {
//                adjustForCurrencyDefaultFractionDigits(format, var5);
//            }

            return format;
        }
    }

//    private static void adjustForCurrencyDefaultFractionDigits(DecimalFormat decimalFormat, DecimalFormatSymbols decimalFormatSymbols) {
//        Currency currency = decimalFormatSymbols.getCurrency();
//        if (currency == null) {
//            try {
//                currency = Currency.getInstance(decimalFormatSymbols.getInternationalCurrencySymbol());
//            } catch (IllegalArgumentException ignored) {
//            }
//        }
//
//        if (currency != null) {
//            int fractionDigits = currency.getDefaultFractionDigits();
//            if (fractionDigits != -1) {
//                int minimumFractionDigits = decimalFormat.getMinimumFractionDigits();
//                if (minimumFractionDigits == decimalFormat.getMaximumFractionDigits()) {
//                    decimalFormat.setMinimumFractionDigits(fractionDigits);
//                    decimalFormat.setMaximumFractionDigits(fractionDigits);
//                } else {
//                    decimalFormat.setMinimumFractionDigits(Math.min(fractionDigits, minimumFractionDigits));
//                    decimalFormat.setMaximumFractionDigits(fractionDigits);
//                }
//            }
//        }
//    }
}
