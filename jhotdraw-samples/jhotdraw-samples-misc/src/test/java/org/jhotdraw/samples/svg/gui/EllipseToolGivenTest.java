package org.jhotdraw.samples.svg.gui;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.samples.svg.gui.JGiven.GivenEllipseTool;
import org.jhotdraw.samples.svg.gui.JGiven.ThenEllipseShouldBeCreated;
import org.jhotdraw.samples.svg.gui.JGiven.WhenUserCreatesEllipse;
import org.junit.Test;

public class EllipseToolGivenTest extends ScenarioTest<GivenEllipseTool, WhenUserCreatesEllipse, ThenEllipseShouldBeCreated> {
    @Test
    public void create_ellipse() {
        given().user_select_ellipse_tool();
        when().user_creates_an_ellipse();
        then().the_ellipse_should_be_created();
    }
}
