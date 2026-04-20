package acscapstone.game;

import com.fazecast.jSerialComm.SerialPort;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.app.state.AppState;
import com.jme3.app.SimpleApplication;
import com.jme3.texture.Texture;
import com.simsilica.lemur.GuiGlobals;
import com.fazecast.*;

import java.util.Scanner;

public class ACScapstone extends SimpleApplication {


    public ACScapstone() {
    }
    public ACScapstone(AppState... initialStates) {
        super(initialStates);
    }
    // Handles the camera's movement, somewhat
    private final CamRotationHelper camRotationHelper = new CamRotationHelper(359f, 179f);
    private final LimitsUI limitsUI = new LimitsUI();
    // x (0), y (1), and z (2) each have upper (0) and lower (1) limits
    private float[][] limits = new float[3][2];

    // Actually runs everything
    @Override
    public void simpleInitApp() {

        // Read the USB
        SerialPort comPort = SerialPort.getCommPort("COM3");
        comPort.setBaudRate(115200);
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 0);
        Scanner in = new Scanner(comPort.getInputStream());

        setDisplayStatView(false);
        setDisplayFps(false);

        Texture tex = assetManager.loadTexture("Textures/man.jpg");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        mat.setTexture("ColorMap", tex);

        initKeys(); // Custom Controls

        flyCam.setDragToRotate(true); // So that you're not tabbed into the game with your mouse
        flyCam.setEnabled(false);

        attachUI(); // Attach all the UI stuffs

        // Generate a bunch of spheres so I can see what is actually happening easier
        TestingHelper x = new TestingHelper();
        x.loadSpheres(rootNode, mat);

        reshape(cam.getWidth(), cam.getHeight());
        simpleUpdate(0f);
        /*

        // For the mesh
        Vector3f[] vertices = new Vector3f[x.values.size()];
        short[] indices = new  short[x.values.size() * 3];

        // Set the values inside vertices
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = x.values.get(i);
            indices[i * 3] = (short)x.values.get(i).x;
            indices[i * 3 + 1] = (short)x.values.get(i).y;
            indices[i * 3 + 2] = (short)x.values.get(i).z;
        }

        Mesh mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createShortBuffer(indices));
        mesh.updateBound();

        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Blue);

        Geometry geometry = new Geometry("mesh1", mesh);
        geometry.setMaterial(mat);

        rootNode.attachChild(geometry);
 */
    }

    // Add controls for the camera
    private void initKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(analogListener, "Left", "Right", "Up", "Down");
    }

    // Listener for custom controls
    private final AnalogListener analogListener = (name, value, tpf) -> {
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
                float angle = (name.equals("RotateLeft") ? value : -value);
                Quaternion q = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
                cam.setRotation(cam.getRotation().mult(q));
                break;
        }
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        cam.setLocation(camRotationHelper.generatePosition());
    };

    // Make all of the UI
    private void attachUI() {
        GuiGlobals.initialize(this); // Initialize the UI
        limitsUI.attachUI(guiNode, cam);
    }

    // Get the value of all of the sliders
    private void getLimits(){
        // All are on a range of 0f - 100f
        limits = limitsUI.getLimits();
    }

    // Set the text on the labels
    private void updateLabels(){
        limitsUI.updateLabels(limits);
    }

    // Called when the window size changes
    @Override
    public void reshape(int w, int h) {
        super.reshape(w, h);
        limitsUI.reshape(w, h);
    }

    // Called like 5ish times per second
    @Override
    public void simpleUpdate(float tpf) {
        getLimits();
        updateLabels();

    }
}
