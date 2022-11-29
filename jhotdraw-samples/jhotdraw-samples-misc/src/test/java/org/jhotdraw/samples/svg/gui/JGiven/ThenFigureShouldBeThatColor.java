package org.jhotdraw.samples.svg.gui.JGiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class ThenFigureShouldBeThatColor extends Stage<ThenFigureShouldBeThatColor> {

    @ExpectedScenarioState
    private DefaultDrawingView view;

    @Test
    public ThenFigureShouldBeThatColor the_figure_should_be_that_color() {
        Figure figure = view.getSelectedFigures().iterator().next();
        assertEquals(new Color(0, 115, 245), figure.getAttributes().get(AttributeKeys.FILL_COLOR));
        return self();
    }
}
