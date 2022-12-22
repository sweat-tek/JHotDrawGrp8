package org.jhotdraw.samples.svg.figures;

import org.jhotdraw.draw.handle.Handle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class SVGEllipseFigureTest {

    private SVGEllipseFigure ellipseFigure;
    private Rectangle2D.Double rectangleBounds;

    @Before
    public void setUp() {
        ellipseFigure = new SVGEllipseFigure(3d, 4d, 10d, 10d);
        rectangleBounds = new Rectangle2D.Double(3d, 4d, 10d, 10d);
    }

    @Test
    public void hasBounds() {
        Rectangle2D.Double bounds = ellipseFigure.getBounds();
        assertEquals(bounds, rectangleBounds);
    }

    @Test
    public void setBounds() {
        Point2D.Double anchor = new Point2D.Double(3.43d, 3.23d);
        Point2D.Double lead = new Point2D.Double(6.22d, 7.54d);
        ellipseFigure.setBounds(anchor, lead);
        assertNotEquals(rectangleBounds, ellipseFigure.getBounds());
    }

    @Test
    public void createHandlesHasLinkHandle() {
        int detailLevel = 2;
        LinkedList<Handle> handles = (LinkedList<Handle>) ellipseFigure.createHandles(detailLevel);
        assertTrue(handles.getLast() instanceof LinkHandle);
    }

    @After
    public void tearDown() {
       ellipseFigure.requestRemove();
    }
}
