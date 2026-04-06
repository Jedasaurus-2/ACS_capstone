package acscapstone.game;


import com.jme3.math.Vector3f;

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


}
