package acscapstone.game;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.BaseStyles;

public class LimitsUI {

    public Slider[] upperLimits =  new Slider[3];
    public Slider[] lowerlimits =  new Slider[3];
    public Checkbox[] applyButtons = new Checkbox[3];
    public Label[] xSliderLabels = new Label[2];
    public Label[] ySliderLabels = new Label[2];
    public Label[] zSliderLabels = new Label[2];

    public LimitsUI() {}

    public void attachUI(Node guiNode, Camera cam) {
        final String[] textForButtons = {"Apply X?","Apply Y?","Apply Z?"};
        final String[] textForLabels = {"Upper: ","Lower: "};
        BaseStyles.loadGlassStyle();
        float windowWidth = cam.getWidth();
        float windowHeight = cam.getHeight();
        for (int x = 0; x < 3; x++) {
            upperLimits[x] = new Slider("glass");
            upperLimits[x].getModel().setMinimum(0);
            upperLimits[x].getModel().setMaximum(100);
            upperLimits[x].setPreferredSize(new Vector3f(250f, 25f, 0f));
            upperLimits[x].getThumbButton().setPreferredSize(new Vector3f(25f, 25f, 0));
            upperLimits[x].setLocalTranslation(windowWidth * 0.5f, windowHeight * 0.5f, 0);
            guiNode.attachChild(upperLimits[x]);

            lowerlimits[x] = new Slider("glass");
            lowerlimits[x].getModel().setMinimum(0);
            lowerlimits[x].getModel().setMaximum(100);
            lowerlimits[x].setPreferredSize(new Vector3f(250f, 25f, 0f));
            lowerlimits[x].getThumbButton().setPreferredSize(new Vector3f(25f, 25f, 0));
            lowerlimits[x].setLocalTranslation(windowWidth * 0.5f, windowHeight * 0.5f, 0);
            guiNode.attachChild(lowerlimits[x]);

            applyButtons[x] = new Checkbox(textForButtons[x], "glass");
            applyButtons[x].setLocalTranslation(windowWidth * 0.5f, windowHeight * 0.5f, 0);
            guiNode.attachChild(applyButtons[x]);

            if (x < 2) {
                xSliderLabels[x] = new Label(textForLabels[x], "glass");
                guiNode.attachChild(xSliderLabels[x]);
                ySliderLabels[x] = new Label(textForLabels[x], "glass");
                guiNode.attachChild(ySliderLabels[x]);
                zSliderLabels[x] = new Label(textForLabels[x], "glass");
                guiNode.attachChild(zSliderLabels[x]);
            }
        }
    }

    public void reshape(int w, int h) {
        for (int x = 0; x < 3; x++) {
            if (upperLimits[x] != null)
                upperLimits[x].setLocalTranslation(
                        0f,
                        h * (x + 1) * 0.1f - upperLimits[x].getPreferredSize().y * 0.5f + 25,
                        0f);
            if (lowerlimits[x] != null)
                lowerlimits[x].setLocalTranslation(
                        0f,
                        h * (x + 1) * 0.1f - lowerlimits[x].getPreferredSize().y * 0.5f,
                        0f);
            if (applyButtons[x] != null)
                applyButtons[x].setLocalTranslation(
                        0f,
                        h * (x + 1) * 0.1f +  applyButtons[x].getPreferredSize().y * 0.5f + 25,
                        0f);
            if (x == 1 && upperLimits[x] != null) {
                if (xSliderLabels[x] != null)
                    xSliderLabels[x].setLocalTranslation(
                            upperLimits[x].getPreferredSize().x,
                        h * (x + 1) * 0.1f - upperLimits[x].getPreferredSize().y * 0.5f + 25,
                        0);
                if (ySliderLabels[x] != null) ySliderLabels[x].setLocalTranslation(upperLimits[x].getPreferredSize().x, h * 0.5f, 0);
                if (zSliderLabels[x] != null) zSliderLabels[x].setLocalTranslation(upperLimits[x].getPreferredSize().x, h * 0.5f, 0);
            }
        }
    }
}
