package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.samples.svg.figures.JGivenRectangle.GivenRectangleToolIsSelected;
import org.jhotdraw.samples.svg.figures.JGivenRectangle.ThenARectangleShouldBeCreated;
import org.jhotdraw.samples.svg.figures.JGivenRectangle.WhenUserUsesRectangleTool;
import org.junit.Test;

public class RectangleToolGivenTest extends ScenarioTest<GivenRectangleToolIsSelected, WhenUserUsesRectangleTool, ThenARectangleShouldBeCreated> {
    @Test
    public void create_rectangle() {
        given().rectangle_tool_is_selected();
        when().user_uses_rectangle_tool();
        then().a_rectangle_should_be_created();
    }
}