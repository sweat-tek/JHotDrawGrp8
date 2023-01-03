package org.jhotdraw.samples.svg.figures;

import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.samples.svg.action.FigureUndoAction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SVGGroupFigureTest {

    private SVGGroupFigure svgGroupFigure;

    Rectangle2D.Double groupBounds;

    @Mock
    private Graphics2D graphics2D;

    @Before
    public void beforeClass() throws Exception {
        graphics2D = mock(Graphics2D.class);
        svgGroupFigure = new SVGGroupFigure();
        groupBounds = svgGroupFigure.getBounds();
    }

    @Test
    public void hasBounds() {
        assertNotNull(groupBounds);
    }

    @Test
    public void transform() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToTranslation(5d, 5d);
        svgGroupFigure.transform(affineTransform);

        assertFalse(svgGroupFigure.getBounds().x == groupBounds.x);
        assertFalse(svgGroupFigure.getBounds().y == groupBounds.y);
    }

    @Test
    public void getActions() {
        // svgGroupFigure must be provided a value for TRANSFORM in order to add FigureUndoAction
        // this is because it cannot be mocked, as it is part of the code unit being tested
        svgGroupFigure.set(new AttributeKey<>("transform", AffineTransform.class), new AffineTransform());
        LinkedList<Action> actionArrayList = (LinkedList<Action>) svgGroupFigure.getActions(null);

        assertTrue(actionArrayList.isEmpty() == false);
        assertTrue(actionArrayList.get(0).getClass() == FigureUndoAction.class);
    }

    @After
    public void tearDown() {
        svgGroupFigure.requestRemove();
    }
}
