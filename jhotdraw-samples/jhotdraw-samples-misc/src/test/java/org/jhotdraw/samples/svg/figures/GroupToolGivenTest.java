package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.samples.svg.figures.JGivenGroup.GivenGroupTool;
import org.jhotdraw.samples.svg.figures.JGivenGroup.ThenGroupShouldBeCreated;
import org.jhotdraw.samples.svg.figures.JGivenGroup.WhenUserCreatesGroup;
import org.junit.Test;

public class GroupToolGivenTest extends ScenarioTest<GivenGroupTool, WhenUserCreatesGroup, ThenGroupShouldBeCreated> {
    @Test
    public void create_group() {
        given().user_select_figures();
        when().user_creates_group();
        then().the_group_should_be_created();
    }
}
