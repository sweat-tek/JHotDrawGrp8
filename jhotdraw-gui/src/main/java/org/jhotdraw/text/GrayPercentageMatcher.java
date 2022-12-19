package org.jhotdraw.text;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrayPercentageMatcher implements RgbMatcher {
    private Matcher matcher;
    private ColorFormatter colorFormatter;
    private String str;
    protected DecimalFormat numberFormat;

    protected static final Pattern RGB_PERCENTAGE_PATTERN = Pattern.compile("^\\s*(?:[rR][gG][bB][%])?\\s*([0-9]{1,3}(?:\\.[0-9]+)?)(?:\\s*,\\s*|\\s+)([0-9]{1,3}(?:\\.[0-9]+)?)(?:\\s*,\\s*|\\s+)([0-9]{1,3}(?:\\.[0-9]+)?)\\s*$");

    public GrayPercentageMatcher(String str, ColorFormatter colorFormatter) {
        this.str = str;
        this.matcher = RGB_PERCENTAGE_PATTERN.matcher(str);
        this.colorFormatter = colorFormatter;
        numberFormat = new DecimalFormat("#.#");
        numberFormat.setDecimalSeparatorAlwaysShown(false);
        numberFormat.setMaximumFractionDigits(1);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        numberFormat.setDecimalFormatSymbols(dfs);
    }

    @Override
    public boolean matches() {
        return matcher.matches();
    }

    @Override
    public Color getColor() throws ParseException {
        colorFormatter.setLastUsedInputFormat(ColorFormatter.Format.RGB_PERCENTAGE);
        try {
            return new Color(
                    numberFormat.parse(matcher.group(1)).floatValue() / 100f,
                    numberFormat.parse(matcher.group(2)).floatValue() / 100f,
                    numberFormat.parse(matcher.group(3)).floatValue() / 100f);
        } catch (NumberFormatException nfe) {
            ParseException pe = new ParseException(str, 0);
            pe.initCause(nfe);
            throw pe;
        } catch (IllegalArgumentException iae) {
            ParseException pe = new ParseException(str, 0);
            pe.initCause(iae);
            throw pe;
        }
    }
}
