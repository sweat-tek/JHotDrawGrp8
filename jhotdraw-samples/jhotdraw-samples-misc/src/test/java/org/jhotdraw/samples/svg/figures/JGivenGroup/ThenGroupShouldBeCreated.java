package org.jhotdraw.samples.svg.figures.JGivenGroup;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGGroupFigure;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ThenGroupShouldBeCreated extends Stage<ThenGroupShouldBeCreated> {

    @ExpectedScenarioState
    private DefaultDrawingView view;

    @Test
    public ThenGroupShouldBeCreated the_group_should_be_created() {
        List<Figure> figureList = view.getDrawing().getFiguresFrontToBack();
        assertTrue(figureList.isEmpty() == false);
        assertTrue(figureList.size() == 1);
        Figure figure = figureList.get(0);
        assertTrue(figure.getClass() == SVGGroupFigure.class);
        return self();
    }
}
