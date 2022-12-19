package org.jhotdraw.text;

import java.awt.*;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RgbHexMatcher implements RgbMatcher {
    private Matcher matcher;
    private ColorFormatter colorFormatter;
    private String str;

    protected static final Pattern RGB_HEX_PATTERN = Pattern.compile("^\\s*(?:[rR][gG][bB]\\s*#|#)\\s*([0-9a-fA-F]{3,6})\\s*$");

    public RgbHexMatcher(String str, ColorFormatter colorFormatter) {
        this.str = str;
        this.matcher = RGB_HEX_PATTERN.matcher(str);
        this.colorFormatter = colorFormatter;
    }

    public boolean matches() {
        return matcher.matches();
    }

    public Color getColor() throws ParseException {
            colorFormatter.setLastUsedInputFormat(ColorFormatter.Format.RGB_HEX);
            try {
                String group1 = matcher.group(1);
                if (group1.length() == 3) {
                    return new Color(Integer.parseInt(
                            "" + group1.charAt(0) + group1.charAt(0)
                                    + group1.charAt(1) + group1.charAt(1)
                                    + group1.charAt(2) + group1.charAt(2),
                            16));
                } else if (group1.length() == 6) {
                    return new Color(Integer.parseInt(group1, 16));
                } else {
                    throw new ParseException("Hex color must have 3 or 6 digits.", 1);
                }
            } catch (NumberFormatException nfe) {
                ParseException pe = new ParseException(str, 0);
                pe.initCause(nfe);
                throw pe;
            }
    }

}
