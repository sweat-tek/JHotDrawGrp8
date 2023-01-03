package org.jhotdraw.samples.svg.action.linetool;

import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import javax.swing.*;

public abstract class AbstractLineToolAction extends AbstractAction {

    protected final SVGPathFigure pathFigure;
    protected final String name;

    protected AbstractLineToolAction(SVGPathFigure figure, String name) {
        super(name);
        this.pathFigure = figure;
        this.name = name;
    }


}
