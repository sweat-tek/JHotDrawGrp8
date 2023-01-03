package org.jhotdraw.samples.svg.figures;

import java.awt.geom.Path2D;

public abstract class SVGRectFigurePathUtil {
    /**
     * The variable acv is used for generating the locations of the control
     * points for the rounded rectangle using path.curveTo.
     */
    private static final double ACV;

    static {
        double angle = Math.PI / 4.0;
        double a = 1.0 - Math.cos(angle);
        double b = Math.tan(angle);
        double c = Math.sqrt(1.0 + b * b) - 1 + a;
        double cv = 4.0 / 3.0 * a * b / c;
        ACV = (1.0 - cv);
    }

    /**
     * We have to generate the path for the round rectangle manually,
     * because the path of a Java RoundRectangle is drawn counter-clockwise
     * whereas an SVG rect needs to be drawn clockwise.
     * @return counter-clockwise path for rectangle with rounded corners
     */
    static Path2D.Double calculateRoundedRectPath(SVGRectFigure svgRectFigure){
        Path2D.Double path = new Path2D.Double();

        double aw = svgRectFigure.getArcWidth() / 2d;
        double ah = svgRectFigure.getArcHeight() / 2d;
        double x = svgRectFigure.getX();
        double y = svgRectFigure.getY();
        double width = svgRectFigure.getWidth();
        double height = svgRectFigure.getHeight();

        path.moveTo((x + aw), (float) y);
        path.lineTo((x + width - aw), (float) y);
        path.curveTo((x + width - aw * ACV), (float) y,
                (x + width), (float) (y + ah * ACV),
                (x + width), (y + ah));
        path.lineTo((x + width), (y + height - ah));
        path.curveTo(
                (x + width), (y + height - ah * ACV),
                (x + width - aw * ACV), (y + height),
                (x + width - aw), (y + height));
        path.lineTo((x + aw), (y + height));
        path.curveTo((x + aw * ACV), (y + height),
                (x), (y + height - ah * ACV),
                (float) x, (y + height - ah));
        path.lineTo((float) x, (y + ah));
        path.curveTo((x), (y + ah * ACV),
                (x + aw * ACV), (float) (y),
                (float) (x + aw), (float) (y));
        path.closePath();

        return path;
    }
}
