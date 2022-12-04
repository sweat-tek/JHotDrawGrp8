/**
 * @(#)PaletteSliderUI.java
 *
 * Copyright (c) 2008 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.gui.plaf.palette;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

/**
 * PaletteSliderUI.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class PaletteSliderUI extends BasicSliderUI {

    private static final float[] ENABLED_STOPS = new float[]{0f, 0.35f, 0.351f, 1f};
    private static final Color[] ENABLED_STOP_COLORS = new Color[]{new Color(0xf3f3f3), new Color(0xcccccc), new Color(0xbababa), new Color(0xf3f3f3)};
    private static final float[] DISABLED_STOPS = new float[]{0f, 0.35f, 0.351f, 1f};
    private static final Color[] DISABLED_STOP_COLORS = new Color[]{new Color(0xf3f3f3), new Color(0xeeeeee), new Color(0xcacaca), new Color(0xf3f3f3)};
    private static final float[] SELECTED_STOPS = new float[]{0f, 0.2f, 1f};
    private static final Color[] SELECTED_STOP_COLORS = new Color[]{new Color(0x999999), new Color(0xaaaaaa), new Color(0x666666)};

    public static ComponentUI createUI(JComponent b) {
        return new PaletteSliderUI((JSlider) b);
    }

    public PaletteSliderUI(JSlider slider) {
        super(slider);
    }

    @Override
    protected void installDefaults(JSlider slider) {
        super.installDefaults(slider);
        PaletteLookAndFeel.installBorder(slider, "Slider.border");
        PaletteLookAndFeel.installColors(slider, "Slider.background", "Slider.foreground");
    }

    @Override
    public Dimension getPreferredHorizontalSize() {
        Dimension horizDim = (Dimension) PaletteLookAndFeel.getInstance().get("Slider.horizontalSize");
        if (horizDim == null) {
            horizDim = new Dimension(100, 21);
        }
        return horizDim;
    }

    @Override
    public Dimension getPreferredVerticalSize() {
        Dimension vertDim = (Dimension) PaletteLookAndFeel.getInstance().get("Slider.verticalSize");
        if (vertDim == null) {
            vertDim = new Dimension(21, 100);
        }
        return vertDim;
    }

    @Override
    public void paint(Graphics gr, JComponent c) {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    public void paintFocus(Graphics g) {
        // empty
    }

    @Override
    public void paintTrack(Graphics g) {
        int cx, cy, cw, ch;
        int pad;
        Rectangle trackBounds = trackRect;
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            horizontalPaintTrack(g, trackBounds);
        } else {
            verticalPainTrack(g, trackBounds);
        }
    }

    private void verticalPainTrack(Graphics g, Rectangle trackBounds) {
        int ch;
        int pad;
        int cx;
        pad = trackBuffer;
        cx = (trackBounds.width / 2) - 2;
        //cy = pad;
        ch = trackBounds.height;
        g.setColor(new Color(slider.isEnabled() ? 0x888888 : 0xaaaaaa));
        g.drawRoundRect(trackBounds.x + cx, trackBounds.y, 5, ch, 5, 5);
    }

    private void horizontalPaintTrack(Graphics g, Rectangle trackBounds) {
        int cy;
        int cw;
        int pad;
        pad = trackBuffer;
        //cx = pad;
        cy = (trackBounds.height / 2) - 2;
        cw = trackBounds.width;
        g.translate(trackBounds.x, trackBounds.y + cy);
        g.setColor(getShadowColor());
        g.drawLine(0, 0, cw - 1, 0);
        g.drawLine(0, 1, 0, 2);
        g.setColor(getHighlightColor());
        g.drawLine(0, 3, cw, 3);
        g.drawLine(cw, 0, cw, 3);
        g.setColor(Color.black);
        g.drawLine(1, 1, cw - 2, 1);
        g.translate(-trackBounds.x, -(trackBounds.y + cy));
    }

    @Override
    public void paintThumb(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        Rectangle knobBounds = thumbRect;
        int w = knobBounds.width;
        int h = knobBounds.height;
        g.translate(knobBounds.x, knobBounds.y);
        float[] stops;
        Color[] stopColors;
        if (slider.isEnabled()) {
            g.setColor(slider.getBackground());
            if (slider.getModel().getValueIsAdjusting()) {
                stops = SELECTED_STOPS;
                stopColors = SELECTED_STOP_COLORS;
            } else {
                stops = ENABLED_STOPS;
                stopColors = ENABLED_STOP_COLORS;
            }
        } else {
            g.setColor(slider.getBackground().darker());
            stops = ENABLED_STOPS;
            stopColors = ENABLED_STOP_COLORS;
        }
        Boolean paintThumbArrowShape
                = (Boolean) slider.getClientProperty("Slider.paintThumbArrowShape");
        if ((!slider.getPaintTicks() && paintThumbArrowShape == null)
                || paintThumbArrowShape == Boolean.FALSE) {
            // "plain" version
            plainPaintThumb(g, w, h, stops, stopColors);
        } else if (slider.getOrientation() == JSlider.HORIZONTAL) {
            int cw = w / 2;
            int[] rect = {1, 1, w - 3, h - 1 - cw};
            int[][] polygon = {{1, h - cw}, {cw - 1, h - 1}, {w - 2, h - 1 - cw}};
            int[] left = {0, 0, w - 2, 0};
            int[] top = {0, 1, 0, h - 1 - cw};
            int[] topSlant = {0, h - cw, cw - 1, h - 1};
            int[] bottom = {w - 1, 0, w - 1, h - 2 - cw};
            int[] bottomSlant = {w - 1, h - 1 - cw, w - 1 - cw, h - 1};
            int[] bottomShadow = {w - 2, 1, w - 2, h - 2 - cw};
            int[] right = {w - 2, h - 1 - cw, w - 1 - cw, h - 2};
            horizontalPaintThumb(g, rect, polygon, top, topSlant, bottomSlant, bottom, bottomShadow, right, left);
        } else {  // vertical
            int cw = h / 2;
            if (slider.getComponentOrientation().isLeftToRight()) {
                int[] rect = {1, 1, w - 1 - cw, h - 3};
                int[][] polygon = {{w - cw - 1, 0}, {w - 1, cw}, {(w - 1 - cw, h - 2}};
                int[] left = {0, 0, 0, h - 2};
                int[] top = {1, 0, w - 1 - cw, 0};
                int[] topSlant = {w - cw - 1, 0, w - 1, cw};
                int[] bottom = {w - 1 - cw, h - 1, w - 1, h - 1 - cw};
                int[] bottomSlant = {0, h - 1, w - 2 - cw, h - 1};
                int[] bottomShadow = {1, h - 2, w - 2 - cw, h - 2};
                int[] right = {w - 1 - cw, h - 2, w - 2, h - cw - 1};
                horizontalPaintThumb(g, rect, polygon, top, topSlant, bottomSlant, bottom, bottomShadow, right, left);
            } else {
                int[] rect = {5, 1, w - 1 - cw, h - 3};
                int[][] polygon = {{cw, 0}, {0, cw}, {cw, h - 2}};
                int[] top = {cw - 1, 0, w - 2, 0};
                int[] topSlant = {0, cw, cw, 0};
                int[] bottomSlant = {0, h - 1, cw, h - 1};
                int[] bottom = {cw, h - 1, w - 1, h - 1};
                int[] bottomShadow = {cw, h - 2, w - 2, h - 2};
                int[] right = {w - 1, 1, w - 1, h - 2};
                horizontalPaintThumb(g, rect, polygon, top, topSlant, bottomSlant, bottom, bottomShadow, right, null);
            }
        }
        g.translate(-knobBounds.x, -knobBounds.y);
    }

    private void horizontalPaintThumb(Graphics2D g, int[] border, int[][] polygon, int[] top, int[] topSlant, int[] bottomSlant, int[] bottom, int[] bottomShadow, int[] right, int[] left) {
        g.fillRect(border[0], border[1], border[2], border[3]);
        Polygon p = new Polygon();
        p.addPoint(polygon[0][0], polygon[0][1]);
        p.addPoint(polygon[1][0],polygon[1][1]);
        p.addPoint(polygon[2][0], polygon[2][1]);
        g.fillPolygon(p);
        g.setColor(getHighlightColor());
        if (left != null) {
            g.drawLine(left[0], left[1], left[2], left[3]);
        }
        g.drawLine(top[0], top[1], top[2], top[3]);
        g.drawLine(topSlant[0], topSlant[1], topSlant[2], topSlant[3]);
        g.setColor(Color.black);
        g.drawLine(bottomSlant[0], bottomSlant[1], bottomSlant[2], bottomSlant[3]);
        g.drawLine(bottom[0], bottom[1], bottom[2], bottom[3]);
        g.setColor(getShadowColor());
        g.drawLine(bottomShadow[0], bottomShadow[1], bottomShadow[2], bottomShadow[3]);
        g.drawLine(right[0], right[1], right[2], right[3]);
    }

    private static void plainPaintThumb(Graphics2D g, int w, int h, float[] stops, Color[] stopColors) {
        LinearGradientPaint lgp = new LinearGradientPaint(
                new Point2D.Float(2, 2), new Point2D.Float(2, 2 + h - 4),
                stops, stopColors,
                MultipleGradientPaint.CycleMethod.REPEAT);
        g.setPaint(lgp);
        g.fillOval(2, 2, w - 4, h - 4);
        g.setColor(new Color(0x444444));
        g.drawOval(1, 1, w - 3, h - 3);
    }

    @Override
    protected Dimension getThumbSize() {
        Dimension size = new Dimension();
        if (slider.getOrientation() == JSlider.VERTICAL) {
            size.width = 15;
            size.height = 15;
        } else {
            size.width = 15;
            size.height = 15;
        }
        return size;
    }
}
