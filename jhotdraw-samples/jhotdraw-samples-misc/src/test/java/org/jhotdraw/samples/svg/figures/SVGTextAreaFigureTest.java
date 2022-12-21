package org.jhotdraw.samples.svg.figures;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.junit.Assert.*;

public class SVGTextAreaFigureTest {

    private SVGTextAreaFigure textArea;

    @Before
    public void setUp() throws Exception {
        textArea = new SVGTextAreaFigure("Start text");
    }

    @After
    public void tearDown() throws Exception {
        textArea.requestRemove();
    }

    @Test
    public void  createTabStops() {
        // This test is made to test if the right amount of tab stops are made
        float tabWidth = 3;
        Rectangle2D.Double textRect = new Rectangle2D.Double(3, 3, 12, 4);
        float[] tabStops = textArea.createTabStops(tabWidth, textRect);
        // The right length is calculated by rectangle width divided by tab width
        int expected = (int) (textRect.width / tabWidth);
        assertEquals(expected, tabStops.length);
    }

    @Test
    public void setBounds() {
        Rectangle2D.Double beforeNewBounds = textArea.getBounds();
        Point2D.Double anchor = new Point2D.Double(3.43, 4.43);
        Point2D.Double lead = new Point2D.Double(5.43, 6.43);
        textArea.setBounds(anchor, lead);
        assertNotEquals(beforeNewBounds, textArea.getBounds());
    }

    @Test
    public void getText() {
        assertEquals("Start text", textArea.getText());
    }

    @Test
    public void setText() {
        textArea.setText("new text");
        assertEquals("new text", textArea.getText());
    }

    @Test
    public void isEmpty() {
        assertFalse(textArea.isEmpty());
        textArea.setText(null);
        assertTrue(textArea.isEmpty());
    }
}