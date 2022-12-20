package org.jhotdraw.text;

import java.awt.*;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RgbIntegerMatcher implements RgbMatcher {
    private Matcher matcher;
    private ColorFormatter colorFormatter;
    private String str;

    protected static final Pattern RGB_INTEGER_PATTERN = Pattern.compile("^\\s*(?:[rR][gG][bB])?\\s*([0-9]{1,3})(?:\\s*,\\s*|\\s+)([0-9]{1,3})(?:\\s*,\\s*|\\s+)([0-9]{1,3})\\s*$");


    public RgbIntegerMatcher(String str, ColorFormatter colorFormatter) {
        this.str = str;
        this.matcher = RGB_INTEGER_PATTERN.matcher(str);
        this.colorFormatter = colorFormatter;
    }

    public boolean matches() {
        return matcher.matches();
    }

    public Color getColor() throws ParseException {
        colorFormatter.setLastUsedInputFormat(ColorFormatter.Format.RGB_INTEGER);
        try {
            return new Color(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)));
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
