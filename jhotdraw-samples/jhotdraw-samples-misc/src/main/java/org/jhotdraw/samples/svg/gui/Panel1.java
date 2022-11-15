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
    private ResourceBundleUtil labels;
    private GridBagLayout layout;
    private JPopupButton opacityPopupButton;
    private JAttributeSlider opacitySlider;
    private GridBagConstraints gbc;
    private Map<AttributeKey<?>, Object> defaultAttributes;
    private AbstractButton btn;
    private JPanel p;


    @Override
    public JPanel getjPanel(DrawingEditor editor, LinkedList<Disposable> disposables) {
        p = new JPanel();
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(5, 5, 5, 8));
        // Abort if no editor is put
        if (editor == null) {
            return p;
        }
        labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        layout = new GridBagLayout();
        p.setLayout(layout);
        // Fill color
        defaultAttributes = new HashMap<AttributeKey<?>, Object>();
        FILL_GRADIENT.put(defaultAttributes, null);
                /*
                btn = ButtonFactory.createSelectionColorButton(editor,
                FILL_COLOR, ButtonFactory.HSB_COLORS_AS_RGB, ButtonFactory.HSB_COLORS_AS_RGB_COLUMN_COUNT,
                "attribute.fillColor", labels, defaultAttributes, new Rectangle(3, 3, 10, 10), disposables);
                 *
                 */
        btn = ButtonFactory.createSelectionColorChooserButton(editor,
                FILL_COLOR, "attribute.fillColor", labels,
                defaultAttributes, new Rectangle(3, 3, 10, 10), PaletteColorChooserUI.class, disposables);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        //((JPopupButton) btn).setAction(null, null);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        p.add(btn, gbc);
        // Opacity slider
        opacityPopupButton = new JPopupButton();
        opacitySlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 100);
        opacityPopupButton.add(opacitySlider);
        labels.configureToolBarButton(opacityPopupButton, "attribute.fillOpacity");
        opacityPopupButton.setUI((PaletteButtonUI) PaletteButtonUI.createUI(opacityPopupButton));
        opacityPopupButton.setIcon(
                new SelectionOpacityIcon(editor, FILL_OPACITY, FILL_COLOR, null, Images.createImage(getClass(), labels.getString("attribute.fillOpacity.largeIcon")),
                        new Rectangle(5, 5, 6, 6), new Rectangle(4, 4, 7, 7)));
        opacityPopupButton.setPopupAnchor(SOUTH_EAST);
        disposables.add(new SelectionComponentRepainter(editor, opacityPopupButton));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weighty = 1f;
        gbc.insets = new Insets(3, 0, 0, 0);
        p.add(opacityPopupButton, gbc);
        opacitySlider.setUI((SliderUI) PaletteSliderUI.createUI(opacitySlider));
        opacitySlider.setScaleFactor(100d);
        disposables.add(new FigureAttributeEditorHandler<Double>(FILL_OPACITY, opacitySlider, editor));
        return p;
    }
}
