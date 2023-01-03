package org.jhotdraw.samples.svg.action.linetool;

import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import java.awt.event.ActionEvent;

import static org.jhotdraw.draw.AttributeKeys.PATH_CLOSED;

public class ClosePathAction extends AbstractLineToolAction{
    private static final long serialVersionUID = 1L;

    public ClosePathAction(SVGPathFigure figure, String name) {
        super(figure, name);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        pathFigure.willChange();
        for (Figure child : pathFigure.getChildren()) {
            pathFigure.getDrawing().fireUndoableEditHappened(
                    PATH_CLOSED.setUndoable(child, true));
        }
        pathFigure.changed();
    }
}
