package org.jhotdraw.samples.svg.figures.JGivenGroup;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.action.GroupAction;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGGroupFigure;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class WhenUserCreatesGroup extends Stage<WhenUserCreatesGroup> {

    @ExpectedScenarioState
    private DefaultDrawingView view;

    @Test
    public WhenUserCreatesGroup user_creates_group() {
        Figure rectFigure_1 = view.getSelectedFigures().iterator().next();
        assertNotNull(rectFigure_1);
        Figure rectFigure_2 = view.getSelectedFigures().iterator().next();
        assertNotNull(rectFigure_2);

        GroupAction GA = new GroupAction(view.getEditor());
        GA.groupFigures(view, new SVGGroupFigure(), view.getSelectedFigures());

        return self();
    }
}
