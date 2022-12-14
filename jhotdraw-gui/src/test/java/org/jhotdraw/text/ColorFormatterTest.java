package org.jhotdraw.text;

import java.awt.*;
import java.text.ParseException;

import static org.junit.Assert.*;

public class ColorFormatterTest {
    private ColorFormatter colorFormatter;
    private String str;
    private Object format;
    private Color color;

    @org.junit.Before
    public void setUp() throws Exception {
        colorFormatter = new ColorFormatter();
        str = "";
        format = null;
        color = null;
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void stringToValue() throws ParseException {
        // Test allow null value returns null
        format = colorFormatter.stringToValue(str);
        assertNull(format);

        str = "22";
        assertThrows(ParseException.class, () -> colorFormatter.stringToValue(str));

        // Test Hex to Color
        str = "#00ff15";
        color = (Color) colorFormatter.stringToValue(str);
        assertNotNull(color);
        assertEquals(0, color.getRed());
        assertEquals(255, color.getGreen());
        assertEquals(21, color.getBlue());

        str = "#FFF";
        color = (Color) colorFormatter.stringToValue(str);
        assertNotNull(color);
        assertEquals(255, color.getRed());
        assertEquals(255, color.getGreen());
        assertEquals(255, color.getBlue());

        // Test RGB to Color
        str = "rgb128,128,128";
        color = (Color) colorFormatter.stringToValue(str);
        assertNotNull(color);
        assertEquals(128, color.getRed());
        assertEquals(128, color.getGreen());
        assertEquals(128, color.getBlue());

        // Test RGB percent to Color
        str = "rgb%43,9,20";
        color = (Color) colorFormatter.stringToValue(str);
        assertNotNull(color);
        assertEquals(110, color.getRed());
        assertEquals(23, color.getGreen());
        assertEquals(51, color.getBlue());
    }

    @org.junit.Test
    public void valueToString() throws ParseException {
        str = "";
        Color color = new Color(128, 128, 128);

        //Test allow null value returns null
        str = colorFormatter.valueToString(color);
        assertNotNull(str);
        assertEquals("128 128 128", str);
    }
}