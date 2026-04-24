package acscapstone.game;


import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.Arrays;

public class TestingHelper {

    public ArrayList<Vector3f> values = new ArrayList<>();

    // Make a bunch of spheres at various locations
    // The locations are determined here . . .
    public TestingHelper() {
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

    // Iterates and makes spheres so I can visualize what I am seeing
    public void loadSpheres(Node node, Material mat) {
        for (Vector3f v : values) {
            Geometry geom2 = new Geometry("Sphere", new Sphere(35, 35, 1f));
            geom2.rotateUpTo(new Vector3f(0f, 0f, -1f));
            geom2.setMaterial(mat);
            geom2.setLocalTranslation(v);

            node.attachChild(geom2);
        }
    }

}
