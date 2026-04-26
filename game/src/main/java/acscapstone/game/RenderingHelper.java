package acscapstone.game;


import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.HashMap;

public class RenderingHelper {

    public ArrayList<Vector3f> values = new ArrayList<>();

    // Make a bunch of spheres at various locations
    // The locations are determined here . . .
    public RenderingHelper() {
        final float converter = 2f * 3.14159265359f / 360f; // From degrees to radians
        for (int v = 0; v < 180; v += 6) {
            for (int h = 0; h < 180; h += 6){
                Vector3f pos = new Vector3f();
                pos.x = (float) (Math.sin(converter * (v - 90f)) * Math.cos(converter * h) * 15f); // Multivariate formula
                pos.z = (float) (Math.sin(converter * (v - 90f)) * Math.sin(converter * h) * 15f);
                pos.y = (float) Math.cos(converter * (v - 90f)) * 15f;
                values.add(pos);
            }
        }
    }

    private Vector3f convertToSpherical(int x, int y, int d) {
        final float converter = 2f * 3.14159265359f / 360f; // From degrees to radians
        Vector3f pos = new Vector3f();
        pos.x = (float) (Math.sin(converter * (y - 90f)) * Math.cos(converter * x) * d); // Multivariate formula
        pos.z = (float) (Math.sin(converter * (y - 90f)) * Math.sin(converter * x) * d);
        pos.y = (float) Math.cos(converter * (y - 90f)) * d;
        return pos;
    }

    private Vector3f convertToSpherical(int x, int y, String d2) {
        int d = Integer.parseInt(d2);
        final float converter = 2f * 3.14159265359f / 360f; // From degrees to radians
        Vector3f pos = new Vector3f();
        pos.x = (float) (Math.sin(converter * (y - 90f)) * Math.cos(converter * x) * d); // Multivariate formula
        pos.z = (float) (Math.sin(converter * (y - 90f)) * Math.sin(converter * x) * d);
        pos.y = (float) Math.cos(converter * (y - 90f)) * d;
        return pos;
    }

    // Iterates and makes spheres so I can visualize what I am seeing
    public void loadSpheres(Node node, AssetManager assetManager, LimitsUI limitsUI) {
        node.detachAllChildren();
        for (Vector3f v : values) {
            if (limitsUI.inRange(v)) {
                Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                int distance = (int) Math.sqrt(v.x * v.x + v.y * v.y + v.z + v.z) / 4;
                mat.setColor("Color", ColorRGBA.fromRGBA255(255 - distance, distance / 2, distance, 0));
                Geometry geom2 = new Geometry("Sphere", new Sphere(35, 35, 1f));
                geom2.rotateUpTo(new Vector3f(0f, 0f, -1f));
                geom2.setMaterial(mat);
                geom2.setLocalTranslation(v); // Set position

                node.attachChild(geom2);
            }
        }
    }

    public void loadValues(HashMap<int[], String> data) {
        this.values = new ArrayList<>();
        for (int x = 6; x <= 180; x += 6) {
            for (int y = 0; y <= 168; y += 6) {
                for (int[] key : data.keySet()) {
                    if (key[0] == x && key[1] == y) {
                        values.add(convertToSpherical(x, y, data.get(key)));
                    }
                }
                if (data.containsKey(new int[]{x, y})) {
                    values.add(convertToSpherical(x, y, data.get(new int[]{x,y})));
                } else {
                    values.add(new Vector3f(999999999f, 999999999f, 999999999f));
                }
            }
        }
        System.out.println(values);
    }
}
