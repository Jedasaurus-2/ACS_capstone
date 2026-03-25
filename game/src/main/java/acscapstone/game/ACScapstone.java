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
    private float hAngle = 0f;
    private float vAngle = 90f;
    private float distance = 10f;

    @Override
    public void simpleInitApp() {
        Geometry geom1 = new Geometry("Sphere", new Sphere(35,35,1f));
        Vector3f v1 = new Vector3f(0f, 0f, -1f);
        geom1.rotateUpTo(v1);
        Texture tex = assetManager.loadTexture("Textures/man.jpg");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        mat.setTexture("ColorMap", tex);
        geom1.setMaterial(mat);

        initKeys();
        flyCam.setDragToRotate(true);

        rootNode.attachChild(geom1);

        GuiGlobals.initialize(this);
        instantiateAllUI();
    }

    private void initKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(analogListener, "Left", "Right", "Up", "Down");
    }

    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            float speed = 0.5f;
            switch (name) {
                case "Left":
                    hAngle += speed;
                    hAngle %= 360;
                    break;
                case "Right":
                     hAngle -= speed;
                     hAngle %= 360;
                    break;
                case "Up":
                    if (!(vAngle - speed < 1)) vAngle -= speed;
                    break;
                case "Down":
                    if (!(vAngle + speed > 179)) vAngle += speed;
                    break;
                case "RotateLeft":
                case "RotateRight":
                    float rotateSpeed = 1f;
                    float angle = (name.equals("RotateLeft") ? value : -value) * rotateSpeed;
                    Quaternion q = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
                    cam.setRotation(cam.getRotation().mult(q));
                    break;
            }
            System.out.println(hAngle + ", " + vAngle);
            cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
            cam.setLocation(getCameraPosition());
        }
    };

    private void initCrosshair() {
        Picture crosshair = new Picture("crosshair");
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

    private void instantiateAllUI(){
        Container myWindow = new Container();
        for (int x = 0; x < 16; x++){
            Label label = new Label("Hello, World");
            myWindow.attachChild(label);
        }
        guiNode.attachChild(myWindow);
    }

    private Vector3f getCameraPosition(){
        Vector3f pos = new Vector3f();
        float converter = 2f * 3.14159265359f / 360f;
        pos.x = (float) (Math.cos(converter * hAngle) * Math.sin(converter * vAngle) * distance);
        pos.z = (float) (Math.sin(converter * hAngle) * Math.sin(converter * vAngle) * distance);
        pos.y = (float) Math.cos(converter * vAngle) * distance;
        return pos;
    }

    private float abs(float x){
        if (x < 0) return x * -1;
        return x;
    }
}

