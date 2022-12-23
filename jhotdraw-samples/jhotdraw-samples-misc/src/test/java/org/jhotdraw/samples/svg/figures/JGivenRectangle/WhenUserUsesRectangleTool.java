package org.jhotdraw.samples.svg.figures.JGivenRectangle;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.tool.CreationTool;
//import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Test;

//import java.awt.*;
import java.awt.event.MouseEvent;

import static java.awt.event.MouseEvent.MOUSE_PRESSED;

public class WhenUserUsesRectangleTool extends Stage<WhenUserUsesRectangleTool> {
    @ExpectedScenarioState
    private DefaultDrawingView defaultDrawingView;

    @ExpectedScenarioState
    private CreationTool creationTool;

    @Test
    public WhenUserUsesRectangleTool when_user_uses_rectangle_tool() {
        creationTool.mousePressed(new MouseEvent(defaultDrawingView, MOUSE_PRESSED, 0, 0, 20, 20, 1, false, 1));
        return self();
    }
}
