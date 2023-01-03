package org.jhotdraw.samples.svg.action.linetool;

import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import java.awt.event.ActionEvent;

import static org.jhotdraw.draw.AttributeKeys.PATH_CLOSED;

public class OpenPathAction extends AbstractLineToolAction {
    private static final long serialVersionUID = 1L;

    public OpenPathAction(SVGPathFigure figure, String name) {
        super(figure, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pathFigure.willChange();
        for (Figure child : pathFigure.getChildren()) {
            pathFigure.getDrawing().fireUndoableEditHappened(
                    PATH_CLOSED.setUndoable(child, false));
        }
        pathFigure.changed();
    }

}
