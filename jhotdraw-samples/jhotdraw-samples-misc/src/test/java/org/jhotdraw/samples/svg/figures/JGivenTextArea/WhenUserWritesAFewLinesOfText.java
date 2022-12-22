package org.jhotdraw.samples.svg.figures.JGivenTextArea;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class WhenUserWritesAFewLinesOfText extends Stage<WhenUserWritesAFewLinesOfText> {

    @ExpectedScenarioState
    private DefaultDrawingView view;

    @Test
    public WhenUserWritesAFewLinesOfText userWritesAFewLinesOfText() {
        Figure textAreaFigure = view.getSelectedFigures().iterator().next();
        assertNotNull(textAreaFigure);
        TextHolderFigure textArea = (TextHolderFigure) textAreaFigure;
        textArea.setText("This text is written by the user");

        return self();
    }
}
