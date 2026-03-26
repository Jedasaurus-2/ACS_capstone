package acscapstone.game;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.app.state.AppState;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;


/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link com.jme3.app.state.BaseAppState}, Custom controls {@link com.jme3.scene.control.AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class ACScapstone extends SimpleApplication {

    public ACScapstone() {
    }
    public ACScapstone(AppState... initialStates) {
        super(initialStates);
    }
    private final CamRotationHelper camRotationHelper = new CamRotationHelper(359f, 179f);
    private final UIHelper uiHelper = new UIHelper();

    @Override
    public void simpleInitApp() {
        // Making a geometry object . .
        Geometry geom1 = new Geometry("Sphere", new Sphere(35,35,1f));
        // Because they are sideways for some reason
        geom1.rotateUpTo(new Vector3f(0f, 0f, -1f));
        Texture tex = assetManager.loadTexture("Textures/man.jpg");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        mat.setTexture("ColorMap", tex);
        geom1.setMaterial(mat);

        initKeys(); // Custom Controls

        flyCam.setDragToRotate(true); // So that you're not tabbed into the game with your mouse

        rootNode.attachChild(geom1); // Actually add the geometry object (3D shape)

        GuiGlobals.initialize(this); // Initialize the UI
        guiNode.attachChild(uiHelper.makeCrosshair(assetManager, settings)); // Call UiHelper to make the crosshair
    }

    private void initKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(analogListener, "Left", "Right", "Up", "Down");
    }

    // Listener for custom controls
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            float speed = 0.5f;
            switch (name) {
                case "Left":
                    camRotationHelper.incrementHAngle(speed);
                    break;
                case "Right":
                     camRotationHelper.incrementHAngle(-speed);
                    break;
                case "Up":
                    camRotationHelper.incrementVAngle(-speed);
                    break;
                case "Down":
                    camRotationHelper.incrementVAngle(speed);
                    break;
                case "RotateLeft":
                case "RotateRight":
                    float rotateSpeed = 1f;
                    float angle = (name.equals("RotateLeft") ? value : -value) * rotateSpeed;
                    Quaternion q = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
                    cam.setRotation(cam.getRotation().mult(q));
                    break;
            }
            cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
            cam.setLocation(camRotationHelper.generatePosition());
        }
    };
}
