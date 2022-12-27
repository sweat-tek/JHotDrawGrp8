package org.jhotdraw.samples.svg.figures;


import org.jhotdraw.draw.handle.Handle;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.Collection;



import static org.junit.Assert.*;

public class SVGPathFigureTest {

    private SVGPathFigure pathFigure;

    @Before
    public void setup(){
        pathFigure = new SVGPathFigure();

    }

    @Test
    public void createActionsTest(){
        Collection<Action> actions = pathFigure.getActions(new Point2D.Double(3, 3));
        assertNotNull(actions);
        assertFalse(actions.isEmpty());
    }

    @Test
    public void cloneTest(){
        SVGPathFigure clone = pathFigure.clone();
        assertNotNull(clone);
        assertEquals(pathFigure.getPath().getWindingRule(), clone.getPath().getWindingRule());
    }

    @Test
    public void createHandlesTest(){
        Collection<Handle> detail_1 = pathFigure.createHandles(-1);
        assertNotNull(detail_1);
        assertEquals(1, detail_1.size());

        Collection<Handle> detail1 = pathFigure.createHandles(1);
        assertNotNull(detail1);
        assertEquals(10, detail1.size());

        Collection<Handle> detail2 = pathFigure.createHandles(2);
        assertNotNull(detail2);
        assertEquals(2, detail2.size());
    }
}
