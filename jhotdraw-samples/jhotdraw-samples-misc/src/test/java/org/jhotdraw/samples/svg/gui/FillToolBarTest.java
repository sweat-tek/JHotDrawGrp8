package org.jhotdraw.samples.svg.gui;

import org.junit.Test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static org.junit.Assert.*;

public class FillToolBarTest {
    private FillToolBar fillToolBar;
    private JComponent component;

    @org.junit.Before
    public void setUp() throws Exception {
        fillToolBar = new FillToolBar();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        fillToolBar.dispose();
    }

    @Test
    public void createDisclosedComponent() {
        component = fillToolBar.createDisclosedComponent(0);
        assertEquals(JPanel.class, component.getClass());
        assertTrue(component.isOpaque());

        component = fillToolBar.createDisclosedComponent(1);
        assertEquals(JPanel.class, component.getClass());
        assertEquals(new EmptyBorder(5, 5, 5, 8).getBorderInsets(), component.getBorder().getBorderInsets(component));
        assertFalse(component.isOpaque());

        component = fillToolBar.createDisclosedComponent(2);
        assertEquals(JPanel.class, component.getClass());
        assertFalse(component.isOpaque());
    }
}