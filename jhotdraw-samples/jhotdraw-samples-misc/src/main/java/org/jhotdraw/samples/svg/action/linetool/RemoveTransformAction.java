package org.jhotdraw.samples.svg.action.linetool;

import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import java.awt.event.ActionEvent;

import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;

public class RemoveTransformAction extends AbstractLineToolAction {

    private static final long serialVersionUID = 1L;

    public RemoveTransformAction(SVGPathFigure figure, String name){
        super(figure, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.pathFigure.willChange();
        this.pathFigure.fireUndoableEditHappened(TRANSFORM.setUndoable(this.pathFigure, null));
        this.pathFigure.changed();
    }
}
