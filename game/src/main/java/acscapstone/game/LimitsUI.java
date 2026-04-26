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
    public Button applyButton;

    public LimitsUI() {}

    // Makes all of the UI
    public void attachUI(Node guiNode, Camera cam) {
        final String[] textForButtons = {"Apply X?","Apply Y?","Apply Z?"};
        final String[] textForLabels = {"Upper: ","Lower: "};
        BaseStyles.loadGlassStyle();

        applyButton = new Button("Apply", "glass");
        guiNode.attachChild(applyButton);

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
            guiNode.attachChild(upperLimits[x]);

            // Same thing again
            lowerLimits[x] = new Slider("glass");
            lowerLimits[x].getModel().setMinimum(0);
            lowerLimits[x].getModel().setMaximum(100);
            lowerLimits[x].setPreferredSize(new Vector3f(250f, 25f, 0f));
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
            if (upperLimits[x] != null) {
                upperLimits[x].setLocalTranslation(
                        0f,
                        h * (x + 1) * 0.1f - upperLimits[x].getPreferredSize().y * 0.5f + 25,
                        0f);
                upperLimits[x].getModel().setValue(100d);
            }
            // Also bottom left corner. Offset by x
            if (lowerLimits[x] != null) {
                lowerLimits[x].setLocalTranslation(
                        0f,
                        h * (x + 1) * 0.1f - lowerLimits[x].getPreferredSize().y * 0.5f,
                        0f);
                lowerLimits[x].getModel().setValue(0d);
            }
            // Right above the upper slider for x, y, and z
            if (applyButtons[x] != null) {
                applyButtons[x].setLocalTranslation(
                        0f,
                        h * (x + 1) * 0.1f + applyButtons[x].getPreferredSize().y * 0.5f + 25,
                        0f);
            }
        }
        // Upper labels
        if (upperLimits[0] != null && xSliderLabels[0] != null && ySliderLabels[0] != null && zSliderLabels[0] != null) {
            xSliderLabels[0].setLocalTranslation(upperLimits[0].getPreferredSize().x, h * 0.1f - upperLimits[0].getPreferredSize().y * 0.5f + 25, 0);
            ySliderLabels[0].setLocalTranslation(upperLimits[0].getPreferredSize().x, h * 0.2f - upperLimits[0].getPreferredSize().y * 0.5f + 25, 0);
            zSliderLabels[0].setLocalTranslation(upperLimits[0].getPreferredSize().x, h * 0.3f - upperLimits[0].getPreferredSize().y * 0.5f + 25, 0);
        }
        // Lower labels
        if (upperLimits[1] != null && xSliderLabels[1] != null && ySliderLabels[1] != null && zSliderLabels[1] != null) {
            xSliderLabels[1].setLocalTranslation(upperLimits[1].getPreferredSize().x, h * 0.1f - upperLimits[1].getPreferredSize().y * 0.5f, 0);
            ySliderLabels[1].setLocalTranslation(upperLimits[1].getPreferredSize().x, h * 0.2f - upperLimits[1].getPreferredSize().y * 0.5f, 0);
            zSliderLabels[1].setLocalTranslation(upperLimits[1].getPreferredSize().x, h * 0.3f - upperLimits[1].getPreferredSize().y * 0.5f, 0);
        }
        if (upperLimits[2] != null && applyButtons[2] != null) applyButton.setLocalTranslation(0,h * (2 + 1) * 0.1f + applyButtons[2].getPreferredSize().y + applyButton.getPreferredSize().y * 0.5f + 25, 0);
    }

    // Set the text of the labels to the sliders' values
    public void updateLabels(float[][] limits) {
        xSliderLabels[0].setText(xSliderLabels[0].getText().substring(0,7) + (int) limits[0][0]);
        xSliderLabels[1].setText(xSliderLabels[1].getText().substring(0,7) + (int) limits[0][1]);
        ySliderLabels[0].setText(ySliderLabels[0].getText().substring(0,7) + (int) limits[1][0]);
        ySliderLabels[1].setText(ySliderLabels[1].getText().substring(0,7) + (int) limits[1][1]);
        zSliderLabels[0].setText(zSliderLabels[0].getText().substring(0,7) + (int) limits[2][0]);
        zSliderLabels[1].setText(zSliderLabels[1].getText().substring(0,7) + (int) limits[2][1]);
    }

    // Are the upper sliders/lower sliders below/above each other when they should not be?
    public void checkLimits() {
        float[][] limits = getLimits();
        for (int i = 0; i < 3; i++) {
            if (limits[i][0] < limits[i][1]) {
                lowerLimits[i].getModel().setValue(limits[i][0]);
            }
        }
    }

    // Return the current slider values as a 2D array
    public float[][] getLimits(){
        float[][] limits = new float[3][2];
        limits[0][0] = (float) upperLimits[0].getModel().getValue();
        limits[0][1] = (float) lowerLimits[0].getModel().getValue();
        limits[1][0] = (float) upperLimits[1].getModel().getValue();
        limits[1][1] = (float) lowerLimits[1].getModel().getValue();
        limits[2][0] = (float) upperLimits[2].getModel().getValue();
        limits[2][1] = (float) lowerLimits[2].getModel().getValue();
        return limits;
    }

    public boolean inRange(Vector3f pos) {
        int magnitude = 1;
        if (applyButtons[0].isChecked()) {
            if (pos.x > magnitude * upperLimits[2].getModel().getValue()) {
                return false;
            }
            if (pos.x < magnitude * (lowerLimits[2].getModel().getValue() - 100)) {
                return false;
            }
        }
        if (applyButtons[1].isChecked()) {
            if (pos.y > magnitude * upperLimits[1].getModel().getValue()) {
                return false;
            }
            if (pos.y < magnitude * (lowerLimits[1].getModel().getValue() - 100)) {
                return false;
            }
        }
        if (applyButtons[2].isChecked()) {
            if (pos.z > magnitude * upperLimits[0].getModel().getValue()) {
                return false;
            }
            if (pos.z < magnitude * (lowerLimits[0].getModel().getValue() - 100)) {
                return false;
            }
        }
        return true;
    }
}
