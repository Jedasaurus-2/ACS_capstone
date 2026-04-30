package acscapstone.game;

import com.fazecast.jSerialComm.SerialPort;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.app.state.AppState;
import com.jme3.app.SimpleApplication;
import com.simsilica.lemur.GuiGlobals;

import java.io.*;
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
    private Scanner in;
    private PrintWriter writer;
    private final SerialPort comPort = SerialPort.getCommPort("COM3");
    private final DataHelper dataHelper = new DataHelper();
    private final RenderingHelper renderingHelper = new RenderingHelper();

    // Actually runs everything
    @Override
    public void simpleInitApp() {
        //
        try {
            File file = new File("units.txt");
            //writer = new FileWriter(file);
            writer = new PrintWriter(new FileWriter(file, true), true);
            writer.println("");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Read the USB
        comPort.setBaudRate(115200);
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 0);

        in = new Scanner(comPort.getInputStream());

        // Remove the bottom left debug panel
        setDisplayStatView(false);
        setDisplayFps(false);

        initKeys(); // Custom Controls

        flyCam.setDragToRotate(true); // So that you're not tabbed into the game with your mouse
        flyCam.setEnabled(false);
        cam.setFrustumFar(5000f);

        attachUI(); // Attach all the UI stuffs

        // Load data from the units file
        dataHelper.generateData(1, limitsUI);

        // Generate a bunch of spheres so I can see what is actually happening easier
        renderingHelper.loadValues(dataHelper.data);
        renderingHelper.loadSpheres(rootNode, assetManager);

        reshape(cam.getWidth(), cam.getHeight());
        simpleUpdate(0f);
    }

    // Add controls for the camera
    private void initKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addListener(analogListener, "Left", "Right", "Up", "Down", "Forward", "Back");
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
            case "Forward":
                camRotationHelper.distance *= 1.01f;
                break;
            case "Back":
                camRotationHelper.distance *= 0.99f;
            case "RotateLeft":
            case "RotateRight":
                float angle = (name.equals("RotateLeft") ? value : -value);
                Quaternion q = new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y);
                cam.setRotation(cam.getRotation().mult(q));
                break;
        }
        cam.lookAt(new Vector3f(0f, 0f, 0f), Vector3f.UNIT_Y);
        cam.setLocation(camRotationHelper.generatePosition());
    };

    // Make all of the UI
    private void attachUI() {
        GuiGlobals.initialize(this); // Initialize the UI
        limitsUI.attachUI(guiNode, cam);
    }

    // Called when the window size changes
    @Override
    public void reshape(int w, int h) {
        super.reshape(w, h);
        limitsUI.reshape(w, h);
    }

    // Called like 10ish times per second
    @Override
    public void simpleUpdate(float tpf)  {
        // Read the stream
        if (in.hasNext()) {
            String data = in.next();
            System.out.println(data);
            // Write it
            try {
                writer.print(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        // Read the user input
        if (limitsUI.applyButton.isPressed()) {
            renderingHelper.loadSpheres(rootNode, assetManager);
            try {
                // Get selected value
                int l = limitsUI.readableLines.getSelectionModel().getSelection();
                l = limitsUI.model.get(l) - 1;
                rerender(l);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Remove all spheres
    public void rerender(int l){
        rootNode.detachAllChildren();
        // Get the new data from user input
        dataHelper.generateData(l, limitsUI);
        // Load the values into limitsUI
        renderingHelper.loadValues(dataHelper.data);
        // Load the spheres
        renderingHelper.loadSpheres(rootNode, assetManager);
    }
}
