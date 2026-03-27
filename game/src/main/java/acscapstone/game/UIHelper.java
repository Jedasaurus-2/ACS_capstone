package acscapstone.game;

import com.jme3.asset.AssetManager;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.Styles;

public class UIHelper {

    public UIHelper() {}

    public Button makeButton(String text, AssetManager assetManager, AppSettings settings) {
        //Button button = new Button(text, new ElementId("button"), "solid_button");
        Button button = new Button(text, new ElementId("red.button"), "style_1");
        button.setLocalTranslation(settings.getWidth() / 2f, settings.getHeight() / 2f, 0f);
        return button;
    }
}
