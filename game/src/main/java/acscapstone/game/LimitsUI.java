package acscapstone.game;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.simsilica.lemur.*;
import com.simsilica.lemur.core.VersionedList;
import com.simsilica.lemur.style.BaseStyles;

import java.util.ArrayList;

public class LimitsUI {

    public Button applyButton;
    // What the ListBox Renders
    public VersionedList<Integer> model = new VersionedList<>();
    public ListBox<Integer> readableLines;
    public Label readableLinesLabel;

    public LimitsUI() {}

    // Makes all of the UI
    public void attachUI(Node guiNode, Camera cam) {
        final String[] textForButtons = {"Apply X?", "Apply Y?", "Apply Z?"};
        final String[] textForLabels = {"Upper: ", "Lower: "};
        BaseStyles.loadGlassStyle();

        applyButton = new Button("Apply", "glass");
        guiNode.attachChild(applyButton);

        float windowWidth = cam.getWidth();
        float windowHeight = cam.getHeight();
        // Because of x, y, and z limits

        readableLines = new ListBox<>(model, "Glass");
        readableLinesLabel = new Label(" - Scans - ", "glass");
        guiNode.attachChild(readableLines);
        guiNode.attachChild(readableLinesLabel);
    }

    // Called when the window resizes
    // Sets the position of everything based on screen width and height (w, h)
    public void reshape(int w, int h) {
        for (int x = 0; x < 3; x++) {
            if (readableLines != null) {
                readableLines.setLocalTranslation(0f, h * 0.9f, 0f);
                readableLines.setVisibleItems(h / 100);
            }
            if (readableLinesLabel != null) {
                readableLinesLabel.setLocalTranslation(0f, h * 0.9f + readableLinesLabel.getPreferredSize().y + 10, 0f);
            }
            if (applyButton != null) {
                applyButton.setLocalTranslation(0f, h * 0.4f, 0f);
            }
        }
    }

    // Sets up the readable lines at the top left using an array of ints
    public void updateReadableLinesLabel(ArrayList<Integer> xs) {
        readableLines.setScrollOnHover(true);
        model.clear();
        for (Integer x : xs) {
            model.add(x + 1);
        }
    }
}
