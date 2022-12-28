package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.samples.svg.figures.JGivenLineTool.GivenUserUsesLineTool;
import org.jhotdraw.samples.svg.figures.JGivenLineTool.ThenALineShouldExist;
import org.jhotdraw.samples.svg.figures.JGivenLineTool.WhenUserCreatesALine;
import org.junit.Test;

public class PathFigureGivenTest extends ScenarioTest<GivenUserUsesLineTool, WhenUserCreatesALine, ThenALineShouldExist> {

    @Test
    public void create_line() {
        given().the_user_uses_the_line_tool();
        when().a_user_creates_a_line();
        then().a_line_should_exist_in_view();
    }
}
