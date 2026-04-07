package acscapstone.game;


import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import java.util.Arrays;

public class TestingHelper {
    public Vector3f[] values;

    public TestingHelper() {
        values = new Vector3f[72];
        for (int x = 0; x < 72; x++) {
            values[x] = new Vector3f( 10f * (float) Math.sin(x),0f, 10f * (float) Math.cos(x));
        }
        System.out.println(Arrays.toString(values));
    }

    public void loadSpheres(Node node, AssetManager assetManager) {

        Texture tex = assetManager.loadTexture("Textures/man.jpg");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        for (Vector3f v : values) {
            Geometry geom2 = new Geometry("Sphere", new Sphere(35, 35, 1f));
            geom2.rotateUpTo(new Vector3f(0f, 0f, -1f));
            geom2.setMaterial(mat);
            geom2.setLocalTranslation(v);

            node.attachChild(geom2);
        }
    }

}
