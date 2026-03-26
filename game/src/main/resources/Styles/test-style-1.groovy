import com.simsilica.lemur.*;
import com.simsilica.lemur.component.*;
import com.jme3.math.*;

// 1. Define a global base for your style
selector("my_custom_style") {
    fontSize = 18
    color = color(1.0, 1.0, 1.0, 1.0) // White text
}

// 2. Style specific components (Labels, Buttons, etc.)
selector("label", "my_custom_style") {
    insets = new Insets3f(2, 2, 2, 2)
}

selector("button", "my_custom_style") {
    // Background: Quad with a specific color
    background = new QuadBackgroundComponent(color(0.2, 0.2, 0.8, 1.0)) // Blue

    // Text colors for different states
    color = color(0.9, 0.9, 0.9, 1.0)
    highlightColor = color(1.0, 1.0, 0.0, 1.0) // Yellow on hover

    insets = new Insets3f(5, 10, 5, 10)
}

// 3. Style a specific ID (e.g., a "danger" button)
selector("danger.button", "my_custom_style") {
    fontSize = 100
    background = new QuadBackgroundComponent(color(0.8, 0.1, 0.1, 1.0)) // Red
}
