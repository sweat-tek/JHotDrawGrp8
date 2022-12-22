package org.jhotdraw.samples.svg.gui.JGiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.tool.CreationTool;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;
import org.jhotdraw.samples.svg.io.DefaultSVGFigureFactory;
import org.junit.Test;

import java.util.HashMap;

public class GivenEllipseTool extends Stage<GivenEllipseTool> {
    @ProvidedScenarioState
    private DrawingEditor editor;

    @ProvidedScenarioState
    private DefaultDrawingView view;

    @ProvidedScenarioState
    private CreationTool tool;

    @BeforeStage
    void before() {
        editor = new DefaultDrawingEditor();
        view = new DefaultDrawingView();
        view.setDrawing(new DefaultDrawing());
        editor.setActiveView(view);
    }

    @Test
    public GivenEllipseTool user_select_ellipse_tool() {
        tool = new CreationTool(new SVGEllipseFigure(), new HashMap<AttributeKey<?>, Object>());
        tool.activate(editor);
        return self();
    }
}
