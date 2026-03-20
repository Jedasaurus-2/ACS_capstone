package acscapstone.game;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Torus;
import com.jme3.app.state.AppState;
import com.jme3.app.SimpleApplication;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

import java.util.ArrayList;


/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link com.jme3.app.state.BaseAppState}, Custom controls {@link com.jme3.scene.control.AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class ACScapstone extends SimpleApplication {

    private Geometry geom1;
    private int incrementer = 2;
    private float timePassed = 0f;
    private ArrayList<Geometry> geoms = new ArrayList<>();
    private Material mat;

    public ACScapstone() {
    }

    public ACScapstone(AppState... initialStates) {
        super(initialStates);
    }

    @Override
    public void simpleInitApp() {
        geom1 = new Geometry("Cube", new Box(1f,1f,1f));
        Texture tex = assetManager.loadTexture("Textures/man.jpg");
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", tex);
        geom1.setMaterial(mat);
        initKeys();
        initCrosshair();
        rootNode.attachChild(geom1);
    }

    private void initKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_LSHIFT));

        inputManager.addListener(analogListener, "Left", "Right", "Forward", "Back", "Up", "Down");
    }

    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            float speed = 10f;
            Vector3f v = new Vector3f(0, 0, 0);
            switch (name) {
                case "Left":
                    v = cam.getLeft().mult(speed * tpf);
                    break;
                case "Right":
                    v = cam.getLeft().mult(-speed * tpf);
                    break;
                case "Forward":
                    v = cam.getDirection().mult(speed * tpf);
                    break;
                case "Back":
                    v = cam.getDirection().mult(-speed * tpf);
                    break;
                case "Up":
                    v = cam.getUp().mult(speed * tpf);
                    break;
                case "Down":
                    v = cam.getUp().mult(-speed * tpf);
                    break;
                case "RotateLeft":
                case "RotateRight":
                    float rotateSpeed = 1f;
                    float angle = (name.equals("RotateLeft") ? value : -value) * rotateSpeed;
                    Quaternion q = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
                    cam.setRotation(cam.getRotation().mult(q));
                    break;
            }
            cam.setLocation(cam.getLocation().addLocal(v));
        }
    };

    private void initCrosshair() {
        Picture crosshair = new Picture("crosshair");
        Texture2D tex = (Texture2D) assetManager.loadTexture("Textures/Crosshair.png");
        crosshair.setImage(assetManager, "Textures/Crosshair.png", true);
        float width = 15;
        float height = 15;
        crosshair.setWidth(width);
        crosshair.setHeight(height);
        float x = (settings.getWidth() / 2f) - (width / 2f);
        float y = (settings.getHeight() / 2f) - (height / 2f);
        crosshair.setPosition(x, y);
        guiNode.attachChild(crosshair);
    }

    @Override
    public void simpleUpdate(float tpf) {
        float rollSpeed = 0.8f * tpf;
        float pitchSpeed = 0.1f * tpf;
        float yawSpeed = 0.1f * tpf;
        geom1.rotate(yawSpeed, rollSpeed, pitchSpeed);
        timePassed += tpf;
        if (timePassed >= 1f) {
            timePassed -= 1f;
            geoms.add(new Geometry("Cube", new Box(1, 1, 1)));
            geoms.getLast().setMaterial(mat);
            geoms.getLast().move(incrementer, 0, 0);
            rootNode.attachChild(geoms.getLast());
            incrementer += 2;
            System.out.println(geoms);
        }
    }
}

