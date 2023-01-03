package org.jhotdraw.samples.svg.action.linetool;

import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import java.awt.event.ActionEvent;

public class FlattenTransformAction extends AbstractLineToolAction {

    private static final long serialVersionUID = 1L;

    public FlattenTransformAction(SVGPathFigure figure, String name) {
        super(figure, name);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        final Object restoreData = pathFigure.getTransformRestoreData();
        UndoableEdit edit = new AbstractUndoableEdit() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getPresentationName() {
                return name;
            }

            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                pathFigure.willChange();
                pathFigure.restoreTransformTo(restoreData);
                pathFigure.changed();
            }

            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                pathFigure.willChange();
                pathFigure.restoreTransformTo(restoreData);
                pathFigure.flattenTransform();
                pathFigure.changed();
            }
        };
        pathFigure.willChange();
        pathFigure.flattenTransform();
        pathFigure.changed();
        pathFigure.fireUndoableEditHappened(edit);
    }
}

