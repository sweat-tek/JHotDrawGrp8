package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.junit.ScenarioTest;

import org.jhotdraw.samples.svg.figures.JGivenTextArea.GivenUserSelectTextArea;
import org.jhotdraw.samples.svg.figures.JGivenTextArea.ThenTheTextShouldBeAdded;
import org.jhotdraw.samples.svg.figures.JGivenTextArea.WhenUserWritesAFewLinesOfText;
import org.junit.Test;

public class TextAreaGivenTest extends ScenarioTest<GivenUserSelectTextArea, WhenUserWritesAFewLinesOfText, ThenTheTextShouldBeAdded> {

    @Test
    public void text_area() {
        given().the_user_select_text_area();
        when().user_writes_a_few_lines_of_text();
        then().the_text_should_be_added();
    }
}
