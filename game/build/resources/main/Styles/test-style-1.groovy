import com.simsilica.lemur.*;
import com.simsilica.lemur.component.*;
import com.jme3.math.*;

// 1. Define a global base for your style
selector("style_1") {
    fontSize = 18
    color = color(0.8, 0.8, 0.8, 1.0) // Grey
    highlightColor = color(1.0, 1.0, 1.0, 1.0) // White

}

selector("button", "solid_button") {
    // Background: Quad with a specific color
    background = new QuadBackgroundComponent(color(0.25, 0.25, 0.25, 1.0)) // Dark Grey
    insets = new Insets3f(5, 10, 5, 10)
}

selector("red.button", "style_1") {
    highlightColor = color(1.0, 0.0, 0.0, 1.0) // RED
}