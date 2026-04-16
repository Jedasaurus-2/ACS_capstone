package acscapstone.game;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Slider;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.style.BaseStyles;

import javax.swing.*;

public class LimitsUI {

    public Slider[] limits =  new Slider[3];


    public LimitsUI() {
    }

    public void instantiateSlider(Node node, Camera cam) {
        float offset = 100f;
        float w = cam.getWidth();
        float h = cam.getHeight();
        Container myPanel = new Container(new SpringGridLayout());
        for (Slider x : limits) {
            // Load a UI
            BaseStyles.loadGlassStyle();

            x = new Slider("glass");
            // Value range
            x.getModel().setMinimum(0);
            x.getModel().setMaximum(100);

            x.setPreferredSize(new Vector3f(250f, 25f, 0f));
            x.getThumbButton().setPreferredSize(new Vector3f(25f, 25f, 0f));
            // Set the position
            x.setLocalTranslation(100, 100, 0);
            myPanel.addChild(x);
            offset += 100f;
        }
    }
}
