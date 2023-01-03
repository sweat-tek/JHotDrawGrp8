package org.jhotdraw.samples.svg.action.linetool;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import java.awt.event.ActionEvent;

import static org.jhotdraw.draw.AttributeKeys.WINDING_RULE;

public class WindingRuleNonZeroAction extends AbstractLineToolAction{

    private static final long serialVersionUID = 1L;

    public WindingRuleNonZeroAction(SVGPathFigure figure, String name) {
        super(figure, name);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        pathFigure.willChange();
        pathFigure.set(WINDING_RULE, AttributeKeys.WindingRule.NON_ZERO);
        pathFigure.changed();
        pathFigure.getDrawing().fireUndoableEditHappened(
                WINDING_RULE.setUndoable(pathFigure, AttributeKeys.WindingRule.NON_ZERO));
    }
}
