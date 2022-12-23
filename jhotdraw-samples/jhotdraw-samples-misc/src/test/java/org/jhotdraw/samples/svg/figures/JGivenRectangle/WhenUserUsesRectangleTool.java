package org.jhotdraw.samples.svg.figures.JGivenRectangle;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.tool.CreationTool;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Test;

public class WhenUserUsesRectangleTool extends Stage<WhenUserUsesRectangleTool> {
    @ExpectedScenarioState
    private DefaultDrawingView defaultDrawingView;

    @ExpectedScenarioState
    private CreationTool creationTool;

    @Test
    public WhenUserUsesRectangleTool when_user_uses_rectangle_tool() {
        SVGRectFigure svgRectFigure = (SVGRectFigure) creationTool.getPrototype().clone();
        defaultDrawingView.getDrawing().add(svgRectFigure);
        return self();
    }
}
