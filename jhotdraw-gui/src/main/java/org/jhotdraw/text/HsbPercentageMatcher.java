package org.jhotdraw.text;

import org.jhotdraw.color.HSBColorSpace;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HsbPercentageMatcher implements RgbMatcher {
    private Matcher matcher;
    private ColorFormatter colorFormatter;
    private String str;
    protected DecimalFormat numberFormat;

    protected static final Pattern RGB_PERCENTAGE_PATTERN = Pattern.compile("^\\s*(?:[rR][gG][bB][%])?\\s*([0-9]{1,3}(?:\\.[0-9]+)?)(?:\\s*,\\s*|\\s+)([0-9]{1,3}(?:\\.[0-9]+)?)(?:\\s*,\\s*|\\s+)([0-9]{1,3}(?:\\.[0-9]+)?)\\s*$");

    public HsbPercentageMatcher(String str, ColorFormatter colorFormatter) {
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
        colorFormatter.setLastUsedInputFormat(ColorFormatter.Format.HSB_PERCENTAGE);
        try {
            return new Color(HSBColorSpace.getInstance(),
                    new float[]{
                            matcher.group(1) == null ? 0f : numberFormat.parse(matcher.group(1)).floatValue() / 360f,
                            matcher.group(2) == null ? 1f : numberFormat.parse(matcher.group(2)).floatValue() / 100f,
                            matcher.group(3) == null ? 1f : numberFormat.parse(matcher.group(3)).floatValue() / 100f},
                    1f);
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
