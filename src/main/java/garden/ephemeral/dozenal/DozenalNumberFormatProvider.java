package garden.ephemeral.dozenal;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.spi.NumberFormatProvider;
import java.util.Currency;
import java.util.Locale;

public class DozenalNumberFormatProvider extends NumberFormatProvider {
    private static final Locale CLOCK_TOWER = new Locale("en", "US", "DOZ");

    private static final int NUMBER_STYLE = 0;
    private static final int CURRENCY_STYLE = 1;
    private static final int PERCENT_STYLE = 2;
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
        return getInstance(locale, CURRENCY_STYLE);
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
        return getInstance(locale, PERCENT_STYLE);
    }

    private NumberFormat getInstance(Locale locale, int style) {
        if (locale == null) {
            throw new NullPointerException();
        } else {
//            LocaleProviderAdapter var3 = LocaleProviderAdapter.forType(this.type);
//            String[] var4 = var3.getLocaleResources(locale).getNumberPatterns();
            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
//            int var6 = style == INTEGER_STYLE ? 0 : style;

            DozenalFormat format = new DozenalFormat();
            switch (style) {
                case NUMBER_STYLE:
                    break;

                case CURRENCY_STYLE:
                    adjustForCurrencyDefaultFractionDigits(format, symbols);
                    break;

                case PERCENT_STYLE:
                    format.setMultiplier(144);
                    format.setMaximumFractionDigits(1);
                    format.setPositiveSuffix("%");
                    format.setNegativeSuffix("%");
                    break;

                case INTEGER_STYLE:
                    format.setMaximumFractionDigits(0);
                    format.setDecimalSeparatorAlwaysShown(false);
                    format.setParseIntegerOnly(true);
                    break;

            }

            return format;
        }
    }

    private static void adjustForCurrencyDefaultFractionDigits(DozenalFormat format, DecimalFormatSymbols symbols) {
        Currency currency = symbols.getCurrency();
        if (currency == null) {
            try {
                currency = Currency.getInstance(symbols.getInternationalCurrencySymbol());
            } catch (IllegalArgumentException ignored) {
            }
        }

        if (currency != null) {
            int fractionDigits = currency.getDefaultFractionDigits();
            if (fractionDigits != -1) {
                int minimumFractionDigits = format.getMinimumFractionDigits();
                if (minimumFractionDigits == format.getMaximumFractionDigits()) {
                    format.setMinimumFractionDigits(fractionDigits);
                    format.setMaximumFractionDigits(fractionDigits);
                } else {
                    format.setMinimumFractionDigits(Math.min(fractionDigits, minimumFractionDigits));
                    format.setMaximumFractionDigits(fractionDigits);
                }
            }
        }
    }
}
