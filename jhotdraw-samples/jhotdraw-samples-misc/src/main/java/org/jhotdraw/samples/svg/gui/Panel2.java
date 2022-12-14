package org.jhotdraw.samples.svg.gui;

import org.jhotdraw.api.app.Disposable;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.event.FigureAttributeEditorHandler;
import org.jhotdraw.draw.event.SelectionComponentRepainter;
import org.jhotdraw.draw.gui.JAttributeSlider;
import org.jhotdraw.draw.gui.JAttributeTextField;
import org.jhotdraw.formatter.JavaNumberFormatter;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.gui.action.ButtonFactory;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.gui.plaf.palette.PaletteColorChooserUI;
import org.jhotdraw.gui.plaf.palette.PaletteFormattedTextFieldUI;
import org.jhotdraw.gui.plaf.palette.PaletteSliderUI;
import org.jhotdraw.text.ColorFormatter;
import org.jhotdraw.util.Images;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.SliderUI;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static javax.swing.SwingConstants.SOUTH_EAST;
import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.FILL_GRADIENT;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.FILL_OPACITY;

public class Panel2 extends Panel {
    private ResourceBundleUtil labels;
    private Map<AttributeKey<?>, Object> defaultAttributes;

    public Panel2() {
        labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        defaultAttributes = new HashMap<AttributeKey<?>, Object>();
    }

    @Override
    public JPanel getjPanel(DrawingEditor editor, LinkedList<Disposable> disposables) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        // Abort if no editor is put
        if (editor == null) {
            return p;
        }
        JPanel p1 = getP1(editor, disposables);
        JPanel p2 = getp2(editor, disposables);
        JPanel p3 = new JPanel(new GridBagLayout());
        p3.setOpaque(false);

        p.setBorder(new EmptyBorder(5, 5, 5, 8));
        p.removeAll();
        p.setLayout(new GridBagLayout());
        // Fill color field and button
        // Opacity field with slider

        GridBagConstraints gbc;
        // Add horizontal strips
        gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        p.add(p1, gbc);
        gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        p.add(p2, gbc);
        gbc = new GridBagConstraints();
        gbc.gridy = 2;
        gbc.weighty = 1f;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        p.add(p3, gbc);
        return p;
    }

    private JPanel getp2(DrawingEditor editor, LinkedList<Disposable> disposables) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        JAttributeTextField<Double> opacityField = new JAttributeTextField<Double>();
        opacityField.setColumns(4);
        opacityField.setToolTipText(labels.getString("attribute.fillOpacity.toolTipText"));
        opacityField.putClientProperty("Palette.Component.segmentPosition", "first");
        opacityField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(opacityField));
        JavaNumberFormatter formatter = new JavaNumberFormatter(0d, 100d, 100d, false, "%");
        formatter.setUsesScientificNotation(false);
        formatter.setMaximumFractionDigits(1);
        opacityField.setFormatterFactory(new DefaultFormatterFactory(formatter));
        opacityField.setHorizontalAlignment(JTextField.LEFT);
        disposables.add(new FigureAttributeEditorHandler<Double>(FILL_OPACITY, opacityField, editor));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(3, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(opacityField, gbc);
        JPopupButton opacityPopupButton = new JPopupButton();
        JAttributeSlider opacitySlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 100);
        opacityPopupButton.add(opacitySlider);
        labels.configureToolBarButton(opacityPopupButton, "attribute.fillOpacity");
        opacityPopupButton.setUI((PaletteButtonUI) PaletteButtonUI.createUI(opacityPopupButton));
        opacityPopupButton.setPopupAnchor(SOUTH_EAST);
        opacityPopupButton.setIcon(
                new SelectionOpacityIcon(editor, FILL_OPACITY, FILL_COLOR, null, Images.createImage(getClass(), labels.getString("attribute.fillOpacity.largeIcon")),
                        new Rectangle(5, 5, 6, 6), new Rectangle(4, 4, 7, 7)));
        opacityPopupButton.setPopupAnchor(SOUTH_EAST);
        disposables.add(new SelectionComponentRepainter(editor, opacityPopupButton));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weighty = 1f;
        gbc.insets = new Insets(3, 0, 0, 0);
        panel.add(opacityPopupButton, gbc);
        opacitySlider.setUI((SliderUI) PaletteSliderUI.createUI(opacitySlider));
        opacitySlider.setScaleFactor(100d);
        disposables.add(new FigureAttributeEditorHandler<Double>(FILL_OPACITY, opacitySlider, editor));
        return panel;
    }

    private JPanel getP1(DrawingEditor editor, LinkedList<Disposable> disposables) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        JAttributeTextField<Color> colorField = new JAttributeTextField<Color>();
        colorField.setColumns(7);
        colorField.setToolTipText(labels.getString("attribute.fillColor.toolTipText"));
        colorField.putClientProperty("Palette.Component.segmentPosition", "first");
        colorField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(colorField));
        colorField.setFormatterFactory(ColorFormatter.createFormatterFactory(ColorFormatter.Format.RGB_INTEGER_SHORT, false, false));
        colorField.setHorizontalAlignment(JTextField.LEFT);
        disposables.add(new FigureAttributeEditorHandler<Color>(FILL_COLOR, defaultAttributes, colorField, editor, true));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(colorField, gbc);
        AbstractButton btn = ButtonFactory.createSelectionColorChooserButton(editor,
                FILL_COLOR, "attribute.fillColor", labels,
                defaultAttributes, new Rectangle(3, 3, 10, 10), PaletteColorChooserUI.class, disposables);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        //((JPopupButton) btn).setAction(null, null);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel.add(btn, gbc);
        FILL_GRADIENT.put(defaultAttributes, null);
        return panel;
    }
}
