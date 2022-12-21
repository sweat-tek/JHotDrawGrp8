package org.jhotdraw.samples.svg.figures;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SVGTextAreaFigureTest {

    private SVGTextAreaFigure textArea;

    @Before
    public void setUp() throws Exception {
        textArea = new SVGTextAreaFigure("start");
    }

    @After
    public void tearDown() throws Exception {
        textArea.requestRemove();
    }

    @Test
    public void lineCompletion() {
    }

    @Test
    public void getText() {
        assertEquals("start", textArea.getText());
    }

    @Test
    public void setText() {
        textArea.setText("new text");
        assertEquals("new text", textArea.getText());
    }
}