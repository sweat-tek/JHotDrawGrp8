package org.jhotdraw.samples.svg.action;

import org.jhotdraw.draw.figure.AbstractFigure;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;

public class FigureUndoAction extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private final AbstractFigure figure;

    public FigureUndoAction(AbstractFigure abstractFigure, String name) {
        super(name);
        this.figure = abstractFigure;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        figure.willChange();
        figure.fireUndoableEditHappened(
                TRANSFORM.setUndoable(figure, null)
        );
        figure.changed();
    }
}
