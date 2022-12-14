package org.jhotdraw.samples.svg.gui;

import org.jhotdraw.api.app.Disposable;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.event.FigureAttributeEditorHandler;
import org.jhotdraw.draw.event.SelectionComponentRepainter;
import org.jhotdraw.draw.gui.JAttributeSlider;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.gui.action.ButtonFactory;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.gui.plaf.palette.PaletteColorChooserUI;
import org.jhotdraw.gui.plaf.palette.PaletteSliderUI;
import org.jhotdraw.util.Images;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.SliderUI;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static javax.swing.SwingConstants.SOUTH_EAST;
import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.FILL_GRADIENT;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.FILL_OPACITY;

public class Panel1 extends Panel {
    private Map<AttributeKey<?>, Object> defaultAttributes;
    private ResourceBundleUtil labels;

    public Panel1() {
        labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        defaultAttributes = new HashMap<AttributeKey<?>, Object>();
    }

    @Override
    public JPanel getjPanel(DrawingEditor editor, LinkedList<Disposable> disposables) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(5, 5, 5, 8));
        // Abort if no editor is put
        if (editor == null) {
            return p;
        }
        p.setLayout(new GridBagLayout());
        // Fill color
        FILL_GRADIENT.put(defaultAttributes, null);
        p.add(getButton(editor, disposables), getGridBagConstraints(0, 2, 0));
        // Opacity slider
        JAttributeSlider opacitySlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 100);
        opacitySlider.setUI((SliderUI) PaletteSliderUI.createUI(opacitySlider));
        opacitySlider.setScaleFactor(100d);

        JPopupButton opacityPopupButton = getOpacityPopupButton(editor, opacitySlider);

        disposables.add(new SelectionComponentRepainter(editor, opacityPopupButton));

        GridBagConstraints gbc = getGridBagConstraints(0, 1, 1f);
        gbc.insets = new Insets(3, 0, 0, 0);
        p.add(opacityPopupButton, gbc);

        disposables.add(new FigureAttributeEditorHandler<Double>(FILL_OPACITY, opacitySlider, editor));
        return p;
    }

    private JPopupButton getOpacityPopupButton(DrawingEditor editor, JAttributeSlider opacitySlider) {
        JPopupButton opacityPopupButton = new JPopupButton();
        opacityPopupButton.add(opacitySlider);
        labels.configureToolBarButton(opacityPopupButton, "attribute.fillOpacity");
        opacityPopupButton.setUI((PaletteButtonUI) PaletteButtonUI.createUI(opacityPopupButton));
        opacityPopupButton.setIcon(
                new SelectionOpacityIcon(editor, FILL_OPACITY, FILL_COLOR, null, Images.createImage(getClass(), labels.getString("attribute.fillOpacity.largeIcon")),
                        new Rectangle(5, 5, 6, 6), new Rectangle(4, 4, 7, 7)));
        opacityPopupButton.setPopupAnchor(SOUTH_EAST);
        return opacityPopupButton;
    }

    private GridBagConstraints getGridBagConstraints(int gridx, int gridwidth, float weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridwidth = gridwidth;
        gbc.weighty = weighty;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        return gbc;
    }

    private AbstractButton getButton(DrawingEditor editor, LinkedList<Disposable> disposables) {
        AbstractButton btn = ButtonFactory.createSelectionColorChooserButton(editor,
                FILL_COLOR, "attribute.fillColor", labels,
                defaultAttributes, new Rectangle(3, 3, 10, 10), PaletteColorChooserUI.class, disposables);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        return btn;
    }
}
