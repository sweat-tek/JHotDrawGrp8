package org.jhotdraw.text;

import java.awt.*;
import java.text.ParseException;

public interface RgbMatcher {

    boolean matches();

    Color getColor() throws ParseException;
}
