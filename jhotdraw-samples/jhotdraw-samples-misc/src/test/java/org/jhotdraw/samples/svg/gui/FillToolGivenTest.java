package org.jhotdraw.samples.svg.gui;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.samples.svg.gui.JGiven.GivenUserSelectAFigure;
import org.jhotdraw.samples.svg.gui.JGiven.ThenFigureShouldBeThatColor;
import org.jhotdraw.samples.svg.gui.JGiven.WhenUserSelectsAColor;
import org.junit.Test;

public class FillToolGivenTest extends ScenarioTest<GivenUserSelectAFigure, WhenUserSelectsAColor, ThenFigureShouldBeThatColor> {

    @Test
    public void fill_figure() {
        given().user_select_a_figure();
        when().user_selects_a_color();
        then().the_figure_should_be_that_color();
    }
}
