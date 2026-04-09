package acscapstone.game;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Slider;
import com.simsilica.lemur.style.BaseStyles;

public class LimitsUI {

    public Slider[] limits =  new Slider[3];


    public LimitsUI() {
    }

    public void instantiateSlider(Node node) {
        float offset = 100f;
        for (Slider x : limits) {
            BaseStyles.loadGlassStyle();

            x = new Slider("glass");
            x.getModel().setMinimum(0);
            x.getModel().setMaximum(100);
            x.setPreferredSize(new Vector3f(250f, 25f, 0f));
            x.getThumbButton().setPreferredSize(new Vector3f(25f, 25f, 0f));
            x.getLocalTranslation().set(250f, offset, 0f);
            node.attachChild(x);
            offset += 100f;
        }
    }
}
