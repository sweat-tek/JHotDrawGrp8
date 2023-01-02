/*
 * @(#)SVGPathFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.samples.svg.figures;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.AbstractAttributedCompositeFigure;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

import org.jhotdraw.draw.*;

import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.PATH_CLOSED;
import static org.jhotdraw.draw.AttributeKeys.STROKE_CAP;
import static org.jhotdraw.draw.AttributeKeys.STROKE_JOIN;
import static org.jhotdraw.draw.AttributeKeys.STROKE_MITER_LIMIT;
import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;
import static org.jhotdraw.draw.AttributeKeys.WINDING_RULE;

import org.jhotdraw.draw.AttributeKeys.WindingRule;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.TransformHandleKit;
import org.jhotdraw.geom.Geom;
import org.jhotdraw.geom.GrowStroke;
import org.jhotdraw.geom.Shapes;
import org.jhotdraw.samples.svg.Gradient;
import org.jhotdraw.samples.svg.SVGAttributeKeys;

import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;
import org.jhotdraw.samples.svg.action.FigureUndoAction;
import org.jhotdraw.samples.svg.action.linetool.*;
import org.jhotdraw.util.*;

/**
 * SVGPath is a composite Figure which contains one or more SVGBezierFigures as its children.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class SVGPathFigure extends AbstractAttributedCompositeFigure implements SVGFigure {

    private static final long serialVersionUID = 1L;
    /**
     * This cached path is used for drawing.
     */
    private transient Path2D.Double cachedPath;

    /**
     * This is used to perform faster hit testing.
     */
    private transient Shape cachedHitShape;

    /**
     * Creates a new instance.
     */
    @FeatureEntryPoint(value = "lineTool")
    public SVGPathFigure() {
        add(new SVGBezierFigure());
        SVGAttributeKeys.setDefaults(this);
    }

    @FeatureEntryPoint(value = "lineTool")
    public SVGPathFigure(boolean isEmpty) {
        if (!isEmpty) {
            add(new SVGBezierFigure());
        }
        SVGAttributeKeys.setDefaults(this);
        setConnectable(false);
    }

    @FeatureEntryPoint(value = "lineTool")
    @Override
    public void draw(Graphics2D g) {
        SVGUtil.draw(this, g);
    }

    @Override
    public void drawFigure(Graphics2D g) {
        AffineTransform savedTransform = null;
        if (get(TRANSFORM) != null) {
            savedTransform = g.getTransform();
            g.transform(get(TRANSFORM));
        }
        Paint paint = SVGAttributeKeys.getFillPaint(this);
        if (paint != null) {
            g.setPaint(paint);
            drawFill(g);
        }
        paint = SVGAttributeKeys.getStrokePaint(this);
        if (paint != null) {
            g.setPaint(paint);
            g.setStroke(SVGAttributeKeys.getStroke(this, AttributeKeys.getScaleFactorFromGraphics(g)));
            drawStroke(g);
        }
        if (get(TRANSFORM) != null) {
            g.setTransform(savedTransform);
        }
    }

    @Override
    protected void drawChildren(Graphics2D g) {
        // empty
    }

    @Override
    public void drawFill(Graphics2D g) {
        g.fill(getPath());
    }

    @Override
    public void drawStroke(Graphics2D g) {
        g.draw(getPath());
    }

    @Override
    protected void invalidate() {
        super.invalidate();
        cachedPath = null;
        cachedDrawingArea = null;
        cachedHitShape = null;
    }

    protected Path2D.Double getPath() {
        if (cachedPath == null) {
            cachedPath = new Path2D.Double();
            cachedPath.setWindingRule(get(WINDING_RULE) == WindingRule.EVEN_ODD ? Path2D.Double.WIND_EVEN_ODD : Path2D.Double.WIND_NON_ZERO);
            for (Figure child : getChildren()) {
                SVGBezierFigure b = (SVGBezierFigure) child;
                cachedPath.append(b.getBezierPath(), false);
            }
        }
        return cachedPath;
    }

    protected Shape getHitShape() {
        if (cachedHitShape == null) {
            cachedHitShape = getPath();
            if (get(FILL_COLOR) == null && get(FILL_GRADIENT) == null) {
                cachedHitShape = SVGAttributeKeys.getHitStroke(this, 1.0).createStrokedShape(cachedHitShape);
            }
        }
        return cachedHitShape;
    }

    @Override
    public Rectangle2D.Double getDrawingArea() {
        if (cachedDrawingArea != null) {
            return (Rectangle2D.Double) cachedDrawingArea.clone();
        }
        double strokeTotalWidth = Math.max(1d, AttributeKeys.getStrokeTotalWidth(this, 1.0));
        double width = strokeTotalWidth / 2d;
        if (get(STROKE_JOIN) == BasicStroke.JOIN_MITER) {
            width *= get(STROKE_MITER_LIMIT);
        } else if (get(STROKE_CAP) != BasicStroke.CAP_BUTT) {
            width += strokeTotalWidth * 2;
        }
        Shape gp = getPath();
        Rectangle2D strokeRect = new Rectangle2D.Double(0, 0, width, width);
        AffineTransform tx = get(TRANSFORM);
        if (tx != null) {
            // We have to use the (rectangular) bounds of the path here,
            // because we draw a rectangular handle over the shape of the figure
            gp = tx.createTransformedShape(gp.getBounds2D());
            strokeRect = tx.createTransformedShape(strokeRect).getBounds2D();
        }
        Rectangle2D rx = gp.getBounds2D();
        Rectangle2D.Double r = (rx instanceof Rectangle2D.Double) ? (Rectangle2D.Double) rx : new Rectangle2D.Double(rx.getX(), rx.getY(), rx.getWidth(), rx.getHeight());
        Geom.grow(r, strokeRect.getWidth(), strokeRect.getHeight());
        cachedDrawingArea = r;

        return (Rectangle2D.Double) cachedDrawingArea.clone();
    }

    @Override
    public boolean contains(Point2D.Double p) {
        if (get(TRANSFORM) != null) {
            try {
                p = (Point2D.Double) get(TRANSFORM).inverseTransform(p, new Point2D.Double());
            } catch (NoninvertibleTransformException ex) {
                ex.printStackTrace();
            }
        }
        boolean isClosed = getChild(0).get(PATH_CLOSED);
        if (isClosed && get(FILL_COLOR) == null && get(FILL_GRADIENT) == null) {
            return getHitShape().contains(p);
        }
        double tolerance = Math.max(2f, AttributeKeys.getStrokeTotalWidth(this, 1.0) / 2d);
        if (isClosed || get(FILL_COLOR) != null || get(FILL_GRADIENT) != null) {
            if (getPath().contains(p)) {
                return true;
            }
            double grow = AttributeKeys.getPerpendicularHitGrowth(this, 1.0);
            GrowStroke gs = new GrowStroke(grow,
                    (AttributeKeys.getStrokeTotalWidth(this, 1.0)
                            * get(STROKE_MITER_LIMIT)));
            if (gs.createStrokedShape(getPath()).contains(p)) {
                return true;
            } else {
                if (isClosed) {
                    return false;
                }
            }
        }
        return Shapes.outlineContains(getPath(), p, tolerance);
    }

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        if (getChildCount() == 1 && getChild(0).getNodeCount() <= 2) {
            SVGBezierFigure b = getChild(0);
            b.setBounds(anchor, lead);
            invalidate();
        } else {
            super.setBounds(anchor, lead);
        }
    }

    @Override
    public void transform(AffineTransform tx) {
        if (get(TRANSFORM) != null
                || (tx.getType() & (AffineTransform.TYPE_TRANSLATION)) != tx.getType()) {
            if (get(TRANSFORM) == null) {
                TRANSFORM.setClone(this, tx);
            } else {
                AffineTransform t = TRANSFORM.getClone(this);
                t.preConcatenate(tx);
                set(TRANSFORM, t);
            }
        } else {
            for (Figure f : getChildren()) {
                f.transform(tx);
            }
            if (get(FILL_GRADIENT) != null
                    && !get(FILL_GRADIENT).isRelativeToFigureBounds()) {
                Gradient g = FILL_GRADIENT.getClone(this);
                g.transform(tx);
                set(FILL_GRADIENT, g);
            }
            if (get(STROKE_GRADIENT) != null
                    && !get(STROKE_GRADIENT).isRelativeToFigureBounds()) {
                Gradient g = STROKE_GRADIENT.getClone(this);
                g.transform(tx);
                set(STROKE_GRADIENT, g);
            }
        }
        invalidate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void restoreTransformTo(Object geometry) {
        invalidate();
        Object[] restoreData = (Object[]) geometry;
        ArrayList<Object> paths = (ArrayList<Object>) restoreData[0];
        for (int i = 0, n = getChildCount(); i < n; i++) {
            getChild(i).restoreTransformTo(paths.get(i));
        }
        TRANSFORM.setClone(this, (AffineTransform) restoreData[1]);
        FILL_GRADIENT.setClone(this, (Gradient) restoreData[2]);
        STROKE_GRADIENT.setClone(this, (Gradient) restoreData[3]);
    }

    @Override
    public Object getTransformRestoreData() {
        ArrayList<Object> paths = new ArrayList<>(getChildCount());
        for (int i = 0, n = getChildCount(); i < n; i++) {
            paths.add(getChild(i).getTransformRestoreData());
        }
        return new Object[]{
                paths,
                TRANSFORM.getClone(this),
                FILL_GRADIENT.getClone(this),
                STROKE_GRADIENT.getClone(this)
        };
    }

    @Override
    public <T> void set(AttributeKey<T> key, T newValue) {
        super.set(key, newValue);
        invalidate();
    }

    @Override
    public boolean isEmpty() {
        for (Figure child : getChildren()) {
            SVGBezierFigure b = (SVGBezierFigure) child;
            if (b.getNodeCount() > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = new LinkedList<>();
        int condition = detailLevel % 2;
        if (condition == -1) {
            handles.add(new SVGPathOutlineHandle(this, true));
        }
        if (condition == 0) {
            handles.add(new SVGPathOutlineHandle(this));
            for (Figure child : getChildren()) {
                handles.addAll(((SVGBezierFigure) child).createHandles(this, detailLevel));
            }
            handles.add(new LinkHandle(this));
        }
        if (condition == 1) {
            TransformHandleKit.addTransformHandles(this, handles);
        }
        return handles;
    }

    @Override
    public Collection<Action> getActions(Point2D.Double p) {
        final ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        LinkedList<Action> actions = new LinkedList<>();
        if (get(TRANSFORM) != null) {
            actions.add(new FigureUndoAction(labels.getString("edit.removeTransform.text"), this));
            actions.add(new FlattenTransformAction(this, labels.getString("edit.flattenTransform.text")));
        }
        if (Boolean.TRUE.equals(getChild(getChildCount() - 1).get(PATH_CLOSED))) {
            actions.add(new OpenPathAction(this, labels.getString("attribute.openPath.text")));
        } else {
            actions.add(new ClosePathAction(this, labels.getString("attribute.closePath.text")));
        }
        if (get(WINDING_RULE) != WindingRule.EVEN_ODD) {
            actions.add(new WindingRuleEvenOddAction(this, labels.getString("attribute.windingRule.evenOdd.text")));
        } else {
            actions.add(new WindingRuleNonZeroAction(this, labels.getString("attribute.windingRule.nonZero.text")));
        }
        return actions;
    }

    // CONNECTING
    // EDITING
    /**
     * Handles a mouse click.
     */
    @Override
    @FeatureEntryPoint(value = "lineTool")
    public boolean handleMouseClick(Point2D.Double p, MouseEvent evt, DrawingView view) {
        if (evt.getClickCount() == 2 && view.getHandleDetailLevel() % 2 == 0) {
            for (Figure child : getChildren()) {
                SVGBezierFigure bf = (SVGBezierFigure) child;
                int index = bf.findSegment(p, 5f / view.getScaleFactor());
                if (index != -1) {
                    bf.handleMouseClick(p, evt, view);
                    evt.consume();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SVGBezierFigure getChild(int index) {
        return (SVGBezierFigure) super.getChild(index);
    }

    @Override
    public SVGPathFigure clone() {
        return (SVGPathFigure) super.clone();
    }

    public void flattenTransform() {
        willChange();
        AffineTransform tx = get(TRANSFORM);
        if (tx != null) {
            for (Figure child : getChildren()) {
                ((SVGBezierFigure) child).flattenTransform();
            }
        }
        if (get(FILL_GRADIENT) != null) {
            get(FILL_GRADIENT).transform(tx);
        }
        if (get(STROKE_GRADIENT) != null) {
            get(STROKE_GRADIENT).transform(tx);
        }
        set(TRANSFORM, null);
        changed();
    }
}
