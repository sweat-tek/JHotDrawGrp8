package org.jhotdraw.samples.svg.action.linetool;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import java.awt.event.ActionEvent;

import static org.jhotdraw.draw.AttributeKeys.WINDING_RULE;

public class WindingRuleEvenOddAction extends AbstractLineToolAction{

    public WindingRuleEvenOddAction(SVGPathFigure figure, String name) {
        super(figure, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pathFigure.willChange();
        pathFigure.getDrawing().fireUndoableEditHappened(
                WINDING_RULE.setUndoable(pathFigure, AttributeKeys.WindingRule.EVEN_ODD));
        pathFigure.changed();
    }
}
