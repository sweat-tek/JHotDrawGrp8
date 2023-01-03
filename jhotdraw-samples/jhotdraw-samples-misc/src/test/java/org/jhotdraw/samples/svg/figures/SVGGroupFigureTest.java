package org.jhotdraw.samples.svg.figures;

import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SVGGroupFigureTest {

    private SVGGroupFigure svgGroupFigure;

    Rectangle2D.Double initialGroupBounds;

    @Mock
    private Graphics2D graphics2D;

    @Before
    public void beforeClass() throws Exception {
        graphics2D = mock(Graphics2D.class);
        svgGroupFigure = new SVGGroupFigure();
        initialGroupBounds = svgGroupFigure.getBounds();
    }

    @Test
    public void hasBounds() {
        assertNotNull(initialGroupBounds);
    }

    @Test
    public void createHandles() {
        assertTrue(svgGroupFigure.createHandles(0).getFirst() instanceof BoundsOutlineHandle);
        assertTrue(svgGroupFigure.createHandles(0).getLast() instanceof LinkHandle);
    }

    @Test
    public void createGroup() {
        assertTrue(svgGroupFigure.isEmpty());
        assertTrue(svgGroupFigure.getBounds().width == 0);

        svgGroupFigure.add(new SVGRectFigure(10d, 10d, 8d, 4d));
        svgGroupFigure.add(new SVGRectFigure(20d, 20d, 8d, 4d));

        assertFalse(svgGroupFigure.isEmpty());
        assertTrue(svgGroupFigure.getBounds().width > 0);
    }

    @After
    public void tearDown() {
        svgGroupFigure.requestRemove();
    }
}
