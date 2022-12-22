package org.jhotdraw.samples.svg.figures.JGivenTextArea;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.samples.svg.figures.SVGTextAreaFigure;
import org.junit.Test;

public class GivenUserSelectTextArea extends Stage<GivenUserSelectTextArea> {

    @ProvidedScenarioState
    private DrawingEditor editor;

    @ProvidedScenarioState
    private DefaultDrawingView view;

    @BeforeStage
    void before() {
        editor = new DefaultDrawingEditor();
        view = new DefaultDrawingView();
        view.setDrawing(new DefaultDrawing());
        editor.setActiveView(view);
    }

    @Test
    public GivenUserSelectTextArea the_user_select_text_area() {
        TextHolderFigure textAreaFigure = new SVGTextAreaFigure();
        view.getDrawing().add(textAreaFigure);
        view.addToSelection(textAreaFigure);
        view.selectAll();

        return self();
    }
}
