package acscapstone.game;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.BaseStyles;

public class LimitsUI {

    public Slider[] upperLimits =  new Slider[3];
    public Slider[] lowerLimits =  new Slider[3];
    public Checkbox[] applyButtons = new Checkbox[3];
    public Label[] xSliderLabels = new Label[2];
    public Label[] ySliderLabels = new Label[2];
    public Label[] zSliderLabels = new Label[2];

    public LimitsUI() {}

    // Makes all of the UI
    public void attachUI(Node guiNode, Camera cam) {
        final String[] textForButtons = {"Apply X?","Apply Y?","Apply Z?"};
        final String[] textForLabels = {"Upper: ","Lower: "};
        BaseStyles.loadGlassStyle();
        float windowWidth = cam.getWidth();
        float windowHeight = cam.getHeight();
        // Because of x, y, and z limits
        for (int x = 0; x < 3; x++) {

            // Make it with the default glass style
            upperLimits[x] = new Slider("glass");
            // Set the values the slider has
            upperLimits[x].getModel().setMinimum(0);
            upperLimits[x].getModel().setMaximum(100);
            // Set how the slider it
            upperLimits[x].setPreferredSize(new Vector3f(250f, 25f, 0f));
            // Set the grabby thing's size
            upperLimits[x].getThumbButton().setPreferredSize(new Vector3f(25f, 25f, 0));
            // Default position, won't ever be seen
            upperLimits[x].setLocalTranslation(windowWidth * 0.5f, windowHeight * 0.5f, 0);
            guiNode.attachChild(upperLimits[x]);

            // Same thing again
            lowerLimits[x] = new Slider("glass");
            lowerLimits[x].getModel().setMinimum(0);
            lowerLimits[x].getModel().setMaximum(100);
            lowerLimits[x].setPreferredSize(new Vector3f(250f, 25f, 0f));
            lowerLimits[x].getThumbButton().setPreferredSize(new Vector3f(25f, 25f, 0));
            lowerLimits[x].setLocalTranslation(windowWidth * 0.5f, windowHeight * 0.5f, 0);
            guiNode.attachChild(lowerLimits[x]);

            // Checkmark boxes
            applyButtons[x] = new Checkbox(textForButtons[x], "glass");
            // Won't be seen
            applyButtons[x].setLocalTranslation(windowWidth * 0.5f, windowHeight * 0.5f, 0);
            guiNode.attachChild(applyButtons[x]);

            if (x < 2) {
                // Makes the labels
                xSliderLabels[x] = new Label(textForLabels[x], "glass");
                guiNode.attachChild(xSliderLabels[x]);
                ySliderLabels[x] = new Label(textForLabels[x], "glass");
                guiNode.attachChild(ySliderLabels[x]);
                zSliderLabels[x] = new Label(textForLabels[x], "glass");
                guiNode.attachChild(zSliderLabels[x]);
            }
        }
    }

    // Called when the window resizes
    // Sets the position of everything based on screen width and height (w, h)
    public void reshape(int w, int h) {
        for (int x = 0; x < 3; x++) {
            // Bottom left corner. Offset by x and 25 (about the height of a slider)
            if (upperLimits[x] != null)
                upperLimits[x].setLocalTranslation(
                        0f,
                        h * (x + 1) * 0.1f - upperLimits[x].getPreferredSize().y * 0.5f + 25,
                        0f);
            // Also bottom left corner. Offset by x
            if (lowerLimits[x] != null)
                lowerLimits[x].setLocalTranslation(
                        0f,
                        h * (x + 1) * 0.1f - lowerLimits[x].getPreferredSize().y * 0.5f,
                        0f);
            // Right above the upper slider for x, y, and z
            if (applyButtons[x] != null)
                applyButtons[x].setLocalTranslation(
                        0f,
                        h * (x + 1) * 0.1f +  applyButtons[x].getPreferredSize().y * 0.5f + 25,
                        0f);
            // Upper labels
            if (x == 0 && upperLimits[x] != null && xSliderLabels[x] != null && ySliderLabels[x] != null && zSliderLabels[x] != null) {
                xSliderLabels[x].setLocalTranslation(upperLimits[x].getPreferredSize().x, h * 0.1f - upperLimits[x].getPreferredSize().y * 0.5f + 25, 0);
                ySliderLabels[x].setLocalTranslation(upperLimits[x].getPreferredSize().x, h * 0.2f - upperLimits[x].getPreferredSize().y * 0.5f + 25, 0);
                zSliderLabels[x].setLocalTranslation(upperLimits[x].getPreferredSize().x, h * 0.3f - upperLimits[x].getPreferredSize().y * 0.5f + 25, 0);
            }
            // Lower labels
            if (x == 1 && upperLimits[x] != null && xSliderLabels[x] != null && ySliderLabels[x] != null && zSliderLabels[x] != null) {
                xSliderLabels[x].setLocalTranslation(upperLimits[x].getPreferredSize().x, h * 0.1f - upperLimits[x].getPreferredSize().y * 0.5f, 0);
                ySliderLabels[x].setLocalTranslation(upperLimits[x].getPreferredSize().x, h * 0.2f - upperLimits[x].getPreferredSize().y * 0.5f, 0);
                zSliderLabels[x].setLocalTranslation(upperLimits[x].getPreferredSize().x, h * 0.3f - upperLimits[x].getPreferredSize().y * 0.5f, 0);
            }
        }
    }

    // Set the text of the labels to the sliders' values
    public void updateLabels(float[][] limits) {
        xSliderLabels[0].setText(xSliderLabels[0].getText().substring(0,7) + limits[0][0]);
        xSliderLabels[1].setText(xSliderLabels[1].getText().substring(0,7) + limits[0][1]);
        ySliderLabels[0].setText(ySliderLabels[0].getText().substring(0,7) + limits[1][0]);
        ySliderLabels[1].setText(ySliderLabels[1].getText().substring(0,7) + limits[1][1]);
        zSliderLabels[0].setText(zSliderLabels[0].getText().substring(0,7) + limits[2][0]);
        zSliderLabels[1].setText(zSliderLabels[1].getText().substring(0,7) + limits[2][1]);
    }
}
