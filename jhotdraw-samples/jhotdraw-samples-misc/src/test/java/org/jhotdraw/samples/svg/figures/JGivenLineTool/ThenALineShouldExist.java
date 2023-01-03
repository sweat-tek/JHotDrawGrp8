package org.jhotdraw.samples.svg.figures.JGivenLineTool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ThenALineShouldExist extends Stage<ThenALineShouldExist> {

    @ExpectedScenarioState
    private DefaultDrawingView dView;

    @Test
    public ThenALineShouldExist a_line_should_exist_in_view(){
        Point point = new Point(1, 1);
        Figure figure = dView.findFigure(point);

        assertNotNull(figure);
        assertTrue(figure instanceof SVGPathFigure);
        return self();
    }
}
