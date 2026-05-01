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
    public void loadSpheres(Node node, AssetManager assetManager) {
        node.detachAllChildren();
        for (Vector3f v : values) {
                Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                int distance = (int)(v.distance(Vector3f.ZERO));
                distance = (int) (distance * 2f);
                mat.setColor("Color", ColorRGBA.fromRGBA255(redFormula(distance), greenFormula(distance), blueFormula(distance), greenFormula(distance)));
                Geometry geom2 = new Geometry("Sphere", new Sphere(3, 3, 1f));
                geom2.rotateUpTo(new Vector3f(0f, 0f, -1f));
                geom2.setMaterial(mat);
                geom2.setLocalTranslation(v); // Set position

                node.attachChild(geom2);
        }
    }

    // Put the values into values based on data
    public void loadValues(HashMap<int[], String> data) {
        values.clear();
        for (int x = 1; x < 180; x += 1) {
            for (int y = 0; y <= 168; y += 1) {
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
        System.out.println(values.size());
    }

    // Assuming distance gets to 800
    private int redFormula(int distance) {
        return (int) Math.pow((distance - 400) / 26f, 2);
    }
    private int greenFormula(int distance) {
        return -redFormula(distance) + 250;
    }
    private int blueFormula(int distance) {
        return (int) (distance * (255/900f));
    }
}
