package acscapstone.game;

import com.jme3.math.Vector3f;

public class CamRotationHelper {
    public float hAngle = 0f;
    public float vAngle = 90f;
    public float distance = 750f;
    public final float hLimit;
    public final float vLimit;

    // Made to simplify ACScapstone.java
    public CamRotationHelper(float hLimit, float vLimit) {
        this.hLimit = hLimit;
        this.vLimit = vLimit;
    }

    // Logic is handled here instead of in the other class
    // Its modded so that is doesn't overflow
    public void incrementHAngle(float increment) {
        hAngle += increment;
        hAngle %= 360;
    }

    // Restraints must be put on so that the camera doesn't end up upside down
    public void incrementVAngle(float increment) {
        if (increment < 0) { // Is this up or down
            if (!(vAngle + increment < 1)) { // Is it within bounds
                vAngle += increment;
            }
            return;
        } // Same idea here
        if (!(vAngle + increment > 179)) vAngle += increment;
    }

    // Makes a 3D position and returns it based on spherical coordinates
    public Vector3f generatePosition(){
        Vector3f pos = new Vector3f();
        final float converter = 3.14159265359f / 180f; // From degrees to radians
        pos.x = (float) (Math.cos(converter * hAngle) * Math.sin(converter * vAngle) * distance); // Multivariate formula
        pos.z = (float) (Math.sin(converter * hAngle) * Math.sin(converter * vAngle) * distance);
        pos.y = (float) Math.cos(converter * vAngle) * distance;
        return pos;
    }
}
