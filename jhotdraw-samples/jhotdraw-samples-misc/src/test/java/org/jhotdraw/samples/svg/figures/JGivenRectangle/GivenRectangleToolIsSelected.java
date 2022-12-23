package org.jhotdraw.samples.svg.figures.JGivenRectangle;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.tool.CreationTool;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Test;

public class GivenRectangleToolIsSelected extends Stage<GivenRectangleToolIsSelected> {
    @ProvidedScenarioState
    private DrawingEditor drawingEditor;

    @ProvidedScenarioState
    private DefaultDrawingView defaultDrawingView;

    @ProvidedScenarioState
    private CreationTool creationTool;

    @BeforeStage
    public void before(){
        drawingEditor = new DefaultDrawingEditor();
        defaultDrawingView = new DefaultDrawingView();
        defaultDrawingView.setDrawing(new DefaultDrawing());
        drawingEditor.setActiveView(defaultDrawingView);
    }

    @Test
    public GivenRectangleToolIsSelected rectangle_tool_is_selected() {
        SVGRectFigure svgRectFigure = new SVGRectFigure(5d, 5d, 10d, 3d);
        creationTool = new CreationTool(new SVGRectFigure(), null);
        creationTool.activate(drawingEditor);
        return self();
    }
}
