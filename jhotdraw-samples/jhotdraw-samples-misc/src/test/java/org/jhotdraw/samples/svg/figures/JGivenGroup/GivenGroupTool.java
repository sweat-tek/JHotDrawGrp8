package org.jhotdraw.samples.svg.figures.JGivenGroup;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Test;

public class GivenGroupTool extends Stage<GivenGroupTool> {
    @ProvidedScenarioState
    private DrawingEditor editor;

    @ProvidedScenarioState
    private DefaultDrawingView view;

    @BeforeStage
    void before() {
        editor = new DefaultDrawingEditor();
        view = new DefaultDrawingView();
        view.setDrawing(new DefaultDrawing());
        editor.add(view);
        editor.setActiveView(view);
    }

    @Test
    public GivenGroupTool user_select_figures() {
        SVGRectFigure rectFigure_1 = new SVGRectFigure();
        SVGRectFigure rectFigure_2 = new SVGRectFigure();
        view.getDrawing().add(rectFigure_1);
        view.getDrawing().add(rectFigure_2);
        view.addToSelection(rectFigure_1);
        view.addToSelection(rectFigure_2);
        view.selectAll();

        return self();
    }
}
