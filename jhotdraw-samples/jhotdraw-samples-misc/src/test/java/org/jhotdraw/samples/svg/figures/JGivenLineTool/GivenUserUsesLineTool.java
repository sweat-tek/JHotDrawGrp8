package org.jhotdraw.samples.svg.figures.JGivenLineTool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.tool.CreationTool;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.jhotdraw.samples.svg.io.DefaultSVGFigureFactory;
import org.junit.Test;

import java.util.HashMap;

public class GivenUserUsesLineTool extends Stage<GivenUserUsesLineTool> {
    @ProvidedScenarioState
    private DefaultDrawingView dView;

    @ProvidedScenarioState
    private DefaultSVGFigureFactory svgFactory;

    @ProvidedScenarioState
    private DefaultDrawingEditor dEditor;

    @ProvidedScenarioState
    private CreationTool creationTool;

    @BeforeStage
    void before(){
        dView = new DefaultDrawingView();
        svgFactory = new DefaultSVGFigureFactory();
        dEditor = new DefaultDrawingEditor();
        dView.setDrawing(new DefaultDrawing());
        dEditor.setActiveView(dView);
    }

    @Test
    public GivenUserUsesLineTool the_user_uses_the_line_tool(){
        creationTool = new CreationTool(new SVGPathFigure(), new HashMap<>());
        creationTool.activate(dEditor);
        return self();
    }
}
