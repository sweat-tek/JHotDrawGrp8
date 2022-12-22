package org.jhotdraw.samples.svg.figures.JGivenEllipse;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.io.DefaultSVGFigureFactory;
import org.junit.Test;

import java.util.HashMap;


public class WhenUserCreatesEllipse extends Stage<WhenUserCreatesEllipse> {
    @ExpectedScenarioState
    private DefaultDrawingView view;


    @Test
    public WhenUserCreatesEllipse user_creates_an_ellipse() {
        DefaultSVGFigureFactory factory = new DefaultSVGFigureFactory();
        Figure figure = factory.createEllipse(10, 10, 5, 5, new HashMap<AttributeKey<?>, Object>());
        view.getDrawing().add(figure);
        return self();
    }
}
