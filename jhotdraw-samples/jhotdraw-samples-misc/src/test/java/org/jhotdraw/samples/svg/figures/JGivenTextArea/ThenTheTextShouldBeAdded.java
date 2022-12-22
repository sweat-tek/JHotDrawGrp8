package org.jhotdraw.samples.svg.figures.JGivenTextArea;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThenTheTextShouldBeAdded extends Stage<ThenTheTextShouldBeAdded> {

    @ExpectedScenarioState
    private DefaultDrawingView view;

    @Test
    public ThenTheTextShouldBeAdded the_text_should_be_added() {
        Figure textAreaFigure = view.getSelectedFigures().iterator().next();
        assertNotNull(textAreaFigure);
        TextHolderFigure textArea = (TextHolderFigure) textAreaFigure;
        assertEquals("This text is written by the user", textArea.getText());
        return self();
    }
}
