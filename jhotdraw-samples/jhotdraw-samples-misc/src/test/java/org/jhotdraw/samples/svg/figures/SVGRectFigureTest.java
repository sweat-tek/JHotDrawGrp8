package org.jhotdraw.samples.svg.figures;

import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.samples.svg.action.FigureUndoAction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SVGRectFigureTest {

    private SVGRectFigure svgRectFigure;
    Rectangle2D.Double initalBounds;

    @Mock
    private Graphics2D graphics2D;


    @Before
    public void beforeClass() throws Exception {
        graphics2D = mock(Graphics2D.class);
        svgRectFigure = new SVGRectFigure(10d, 10d, 8d, 4d);
        initalBounds = svgRectFigure.getBounds();
    }

    @Test
    public void hasBounds() {
        assertNotNull(initalBounds);
    }

    @Test
    public void transform() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToTranslation(5d, 5d);
        svgRectFigure.transform(affineTransform);

        assertFalse(svgRectFigure.getBounds().x == initalBounds.x);
        assertFalse(svgRectFigure.getBounds().y == initalBounds.y);
    }

    @Test
    public void drawStroke() {
        svgRectFigure.setArc(2d,2d);
        svgRectFigure.drawStroke(graphics2D);
        ArgumentCaptor<Path2D> captor = ArgumentCaptor.forClass(Path2D.class);
        verify(graphics2D).draw(captor.capture());
        Object parameter = captor.getValue();

        assertNotNull(parameter);
        assertTrue(parameter.getClass() == Path2D.Double.class);
    }

    @Test
    public void getActions() {
        // svgRectFigure must be provided a value for TRANSFORM in order to add FigureUndoAction
        // this is because it cannot be mocked, as it is part of the code unit being tested
        svgRectFigure.set(new AttributeKey<>("transform", AffineTransform.class), new AffineTransform());
        LinkedList<Action> actionArrayList = (LinkedList<Action>) svgRectFigure.getActions(null);

        assertTrue(actionArrayList.isEmpty() == false);
        assertTrue(actionArrayList.get(0).getClass() == FigureUndoAction.class);
    }
}
