package acscapstone.game;


import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.util.Arrays;

public class TestingHelper {
    public Vector3f[] values;

    // Make a bunch of spheres at various locations
    // The locations are determined here . . .
    public TestingHelper() {
        values = new Vector3f[72];
        final float converter = 3.14159265359f / 180f; // From degrees to radians
        for (int x = 0; x < 72; x++) {
            Vector3f pos = new Vector3f();
            pos.x = (float) (Math.cos(converter * 5f * x) * Math.sin(converter * 15f * x) * 15f); // Multivariate formula
            pos.z = (float) (Math.sin(converter * 15f * x) * Math.sin(converter * 5f * x) * 15f);
            pos.y = (float) Math.cos(converter * 7.5f * x) * 15f;
            values[x] = pos;
        }
        System.out.println(Arrays.toString(values));
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
