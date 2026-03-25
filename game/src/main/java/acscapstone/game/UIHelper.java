package acscapstone.game;

import com.jme3.asset.AssetManager;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

public class UIHelper {

    public UIHelper() {}

    public Picture makeCrosshair(AssetManager assetManager, AppSettings settings) {
        Picture crosshair = new Picture("crosshair");
        crosshair.setImage(assetManager, "Textures/Crosshair.png", true);
        float width = 30;
        float height = 30;
        crosshair.setWidth(width);
        crosshair.setHeight(height);
        float x = (settings.getWidth() / 2f) - (width / 2f);
        float y = (settings.getHeight() / 2f) - (height / 2f);
        crosshair.setPosition(x, y);
        return crosshair;
    }
}
