package org.jhotdraw.samples.svg.figures.JGivenEllipse;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ThenEllipseShouldBeCreated extends Stage<ThenEllipseShouldBeCreated> {
    @ExpectedScenarioState
    private DefaultDrawingView view;

    @Test
    public ThenEllipseShouldBeCreated the_ellipse_should_be_created() {
        Figure figure = view.findFigure(new Point(10, 10));

        assertNotNull(figure);
        assertTrue(figure instanceof SVGEllipseFigure);
        return self();
    }
}
