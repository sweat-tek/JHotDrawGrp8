package org.jhotdraw.samples.svg.figures.JGivenRectangle;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class ThenARectangleShouldBeCreated extends Stage<ThenARectangleShouldBeCreated> {
    @ExpectedScenarioState
    private DefaultDrawingView defaultDrawingView;

    @Test
    public ThenARectangleShouldBeCreated then_a_rectangle_should_be_created() {
        List<Figure> figureList = defaultDrawingView.getDrawing().getFiguresFrontToBack();
        assertTrue(figureList.isEmpty() == false);
        Figure figure = figureList.get(0);
        assertTrue(figure.getClass() == SVGRectFigure.class);
        return self();
    }
}
