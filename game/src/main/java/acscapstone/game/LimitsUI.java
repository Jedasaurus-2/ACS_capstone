package acscapstone.game;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Slider;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.style.BaseStyles;

import javax.swing.*;

public class LimitsUI {

    public Slider[] limits =  new Slider[3];


    public LimitsUI() {
    }

    public void attachUI(Node guiNode, Camera cam) {
        BaseStyles.loadGlassStyle();
        float windowWidth = cam.getWidth();
        float windowHeight = cam.getHeight();
        for (int x = 0; x < limits.length; x++) {
            limits[x] = new Slider("glass");
            limits[x].getModel().setMinimum(0);
            limits[x].getModel().setMaximum(100);
            limits[x].setPreferredSize(new Vector3f(250f, 25f, 0f));
            limits[x].getThumbButton().setPreferredSize(new Vector3f(25f, 25f, 0));
            limits[x].setLocalTranslation(windowWidth * 0.5f, windowHeight * 0.5f, 0);
            guiNode.attachChild(limits[x]);
        }
    }

    public void reshape(int w, int h) {
        for (int x = 0; x < limits.length; x++) {
            if (limits[x] != null)
                limits[x].setLocalTranslation(
                        w * 0.5f - limits[x].getPreferredSize().x / 2,
                        h * (x + 1) * 0.1f  - limits[x].getPreferredSize().y / 2,
                        0f);
        }
    }
}
