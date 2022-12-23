package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.samples.svg.figures.JGivenEllipse.GivenEllipseTool;
import org.jhotdraw.samples.svg.figures.JGivenEllipse.ThenEllipseShouldBeCreated;
import org.jhotdraw.samples.svg.figures.JGivenEllipse.WhenUserCreatesEllipse;
import org.jhotdraw.samples.svg.figures.JGivenRectangle.GivenRectangleToolIsSelected;
import org.jhotdraw.samples.svg.figures.JGivenRectangle.ThenARectangleShouldBeCreated;
import org.jhotdraw.samples.svg.figures.JGivenRectangle.WhenUserUsesRectangleTool;
import org.junit.Test;

public class RectangleToolGivenTest extends ScenarioTest<GivenRectangleToolIsSelected, WhenUserUsesRectangleTool, ThenARectangleShouldBeCreated> {
    @Test
    public void create_ellipse() {
        given().rectangle_tool_is_selected();
        when().when_user_uses_rectangle_tool();
        then().then_a_rectangle_should_be_created();
    }
}