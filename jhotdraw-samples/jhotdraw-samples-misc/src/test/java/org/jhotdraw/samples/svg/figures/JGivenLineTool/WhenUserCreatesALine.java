package org.jhotdraw.samples.svg.figures.JGivenLineTool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.jhotdraw.samples.svg.io.DefaultSVGFigureFactory;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;

public class WhenUserCreatesALine extends Stage<WhenUserCreatesALine> {

    @ExpectedScenarioState
    private DefaultDrawingView dView;

    @ExpectedScenarioState
    private DefaultSVGFigureFactory svgFactory;

    @Test
    public WhenUserCreatesALine a_user_creates_a_line(){
        Figure figure = svgFactory.createLine(0, 0, 2, 2, new HashMap<>());
        dView.getDrawing().add(figure);
        Point point = new Point(1, 1);
        Figure foundFigure = dView.findFigure(point);
        assertNotNull(figure);
        assertNotNull(foundFigure);
        assertTrue(foundFigure instanceof SVGPathFigure);
        assertSame(foundFigure, figure);
        return self();
    }

}
