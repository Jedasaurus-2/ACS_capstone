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

    public LimitsUI() {
    }

    public void attachUI(Node guiNode, Camera cam) {
        final String[] text = {"Apply X?","Apply Y?","Apply Z?"};
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

            applyButtons[x] = new Checkbox(text[x], "glass");
            applyButtons[x].setLocalTranslation(windowWidth * 0.5f, windowHeight * 0.5f, 0);
            guiNode.attachChild(applyButtons[x]);
        }
    }

    public void reshape(int w, int h) {
        for (int x = 0; x < 3; x++) {
            if (upperLimits[x] != null)
                upperLimits[x].setLocalTranslation(
                        w * 0.33f - upperLimits[x].getPreferredSize().x / 2,
                        h * (x + 1) * 0.1f  - upperLimits[x].getPreferredSize().y / 2,
                        0f);
            if (lowerlimits[x] != null)
                lowerlimits[x].setLocalTranslation(
                        w * 0.67f - lowerlimits[x].getPreferredSize().x / 2,
                        h * (x + 1) * 0.1f  - lowerlimits[x].getPreferredSize().y / 2,
                        0f);
            if (applyButtons[x] != null)
                applyButtons[x].setLocalTranslation(
                        w * 0.5f - applyButtons.length * 0.5f,
                        h * (x + 3) * 0.1f,
                         0f);
        }
    }
}
