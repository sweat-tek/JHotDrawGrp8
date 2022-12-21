/*
 * @(#)SVGEllipse.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.draw.*;
import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.TransformHandleKit;
import org.jhotdraw.geom.Geom;
import org.jhotdraw.geom.GrowStroke;
import org.jhotdraw.samples.svg.Gradient;
import org.jhotdraw.samples.svg.SVGAttributeKeys;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;

/**
 * SVGEllipse represents a SVG ellipse and a SVG circle element.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class SVGEllipseFigure extends SVGAttributedFigure implements SVGFigure {

    private static final long serialVersionUID = 1L;
    private Ellipse2D.Double ellipse;
    /**
     * This is used to perform faster drawing and hit testing.
     */
    private transient Shape cachedTransformedShape;
    /**
     * This is used to perform faster hit testing.
     */
    private transient Shape cachedHitShape;

    /**
     * Creates a new instance.
     */

    public SVGEllipseFigure() {
        this(0, 0, 0, 0);
    }

    @FeatureEntryPoint(value = "Ellipse")
    public SVGEllipseFigure(double x, double y, double width, double height) {
        ellipse = new Ellipse2D.Double(x, y, width, height);
        SVGAttributeKeys.setDefaults(this);
        setConnectable(false);
    }

    // DRAWING
    @Override
    protected void drawFill(Graphics2D g) {
        if (ellipse.width > 0 && ellipse.height > 0) {
            g.fill(ellipse);
        }
    }

    @Override
    @FeatureEntryPoint(value = "Ellipse")
    protected void drawStroke(Graphics2D g) {
        if (ellipse.width > 0 && ellipse.height > 0) {
            g.draw(ellipse);
        }
    }

    // SHAPE AND BOUNDS
    public double getX() {
        return ellipse.x;
    }

    public double getY() {
        return ellipse.y;
    }

    public double getWidth() {
        return ellipse.getWidth();
    }

    public double getHeight() {
        return ellipse.getHeight();
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return (Rectangle2D.Double) ellipse.getBounds2D();
    }

    @Override
    public Rectangle2D.Double getDrawingArea() {
        Rectangle2D.Double boundingBox = (Rectangle2D.Double) getTransformedShape().getBounds2D();
        double size = SVGAttributeKeys.getPerpendicularHitGrowth(this, 1.0) * 2d + 1;
        AffineTransform transform = get(TRANSFORM);
        if (transform != null) {
            double strokeTotalWidth = AttributeKeys.getStrokeTotalWidth(this, 1.0) / 2d;
            size = strokeTotalWidth * Math.max(transform.getScaleX(), transform.getScaleY()) + 1;
        }
        Geom.grow(boundingBox, size, size);
        return boundingBox;
    }

    /**
     * Checks if a Point2D.Double is inside the figure.
     */
    @Override
    public boolean contains(Point2D.Double p) {
        return getHitShape().contains(p);
    }

    private Shape getTransformedShape() {
        if (cachedTransformedShape == null) {
            if (get(TRANSFORM) == null) {
                cachedTransformedShape = ellipse;
            } else {
                cachedTransformedShape = get(TRANSFORM).createTransformedShape(ellipse);
            }
        }
        return cachedTransformedShape;
    }

    private Shape getHitShape() {
        if (cachedHitShape == null) {
            if (get(FILL_COLOR) != null || get(FILL_GRADIENT) != null) {
                cachedHitShape = new GrowStroke(
                        (float) SVGAttributeKeys.getStrokeTotalWidth(this, 1.0) / 2f,
                        (float) SVGAttributeKeys.getStrokeTotalMiterLimit(this, 1.0)).createStrokedShape(getTransformedShape());
            } else {
                cachedHitShape = SVGAttributeKeys.getHitStroke(this, 1.0).createStrokedShape(getTransformedShape());
            }
        }
        return cachedHitShape;
    }

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        ellipse.x = Math.min(anchor.x, lead.x);
        ellipse.y = Math.min(anchor.y, lead.y);
        ellipse.width = Math.max(0.1, Math.abs(lead.x - anchor.x));
        ellipse.height = Math.max(0.1, Math.abs(lead.y - anchor.y));
        invalidate();
    }

    /**
     * Transforms the figure.
     *
     * @param tx the transformation.
     */
    @Override
    public void transform(AffineTransform tx) {
        AffineTransform tForm = get(TRANSFORM);
        int txType = tx.getType();
        if (tForm != null || (txType & (AffineTransform.TYPE_TRANSLATION)) != txType) {
            if (tForm == null) {
                TRANSFORM.setClone(this, tx);
            } else {
                AffineTransform transform = TRANSFORM.getClone(this);
                transform.preConcatenate(tx);
                set(TRANSFORM, transform);
            }
        } else {
            Point2D.Double anchor = getStartPoint();
            Point2D.Double lead = getEndPoint();
            Point2D.Double transformedAnchor = (Point2D.Double) tx.transform(anchor, anchor);
            Point2D.Double transformedLead = (Point2D.Double) tx.transform(lead, lead);
            setBounds(transformedAnchor, transformedLead);
            transformGradients(tx);
        }
        invalidate();
    }

    private void transformGradients(AffineTransform tx) {
        AttributeKey<Gradient> gradientTransformType = null;
        if (get(FILL_GRADIENT) != null && !get(FILL_GRADIENT).isRelativeToFigureBounds()) {
            gradientTransformType = FILL_GRADIENT;
        }
        if (get(STROKE_GRADIENT) != null && !get(STROKE_GRADIENT).isRelativeToFigureBounds()) {
            gradientTransformType = STROKE_GRADIENT;
        }
        assert gradientTransformType != null;
        transformSpecificGradient(gradientTransformType, tx);
    }
    private void transformSpecificGradient(AttributeKey<Gradient> type, AffineTransform tx) {
        Gradient g = type.getClone(this);
        g.transform(tx);
        set(type, g);
    }

    @Override
    public void restoreTransformTo(Object geometry) {
        Object[] restoreData = (Object[]) geometry;
        ellipse = (Ellipse2D.Double) ((Ellipse2D.Double) restoreData[0]).clone();
        TRANSFORM.setClone(this, (AffineTransform) restoreData[1]);
        FILL_GRADIENT.setClone(this, (Gradient) restoreData[2]);
        STROKE_GRADIENT.setClone(this, (Gradient) restoreData[3]);
        invalidate();
    }

    @Override
    public Object getTransformRestoreData() {
        return new Object[]{
            ellipse.clone(),
            TRANSFORM.getClone(this),
            FILL_GRADIENT.getClone(this),
            STROKE_GRADIENT.getClone(this)};
    }

    // ATTRIBUTES
    // EDITING
    @Override
    public Collection<Handle> createHandles(int detailLevel) {
        int handlesDetailLevel = detailLevel % 2;
        LinkedList<Handle> handles = (LinkedList<Handle>) super.createHandles(handlesDetailLevel);

        if (handlesDetailLevel == 0) {
            handles.add(new LinkHandle(this));
        }

        if (handlesDetailLevel == 1) {
            TransformHandleKit.addTransformHandles(this, handles);
        }

        return handles;
    }

    // CONNECTING
    // COMPOSITE FIGURES
    // CLONING
    @Override
    public SVGEllipseFigure clone() {
        SVGEllipseFigure that = (SVGEllipseFigure) super.clone();
        that.ellipse = (Ellipse2D.Double) this.ellipse.clone();
        that.cachedTransformedShape = null;
        return that;
    }

    // EVENT HANDLING
    @Override
    public boolean isEmpty() {
        Rectangle2D.Double b = getBounds();
        return b.width <= 0 || b.height <= 0;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        cachedTransformedShape = null;
        cachedHitShape = null;
    }
}
