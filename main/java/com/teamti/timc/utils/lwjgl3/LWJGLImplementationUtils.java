package com.teamti.timc.utils.lwjgl3;

import org.lwjgl.opengl.InputImplementation;

import com.teamti.timc.utils.lwjgl3.glfw.GLFWKeyboardImplementation;
import com.teamti.timc.utils.lwjgl3.glfw.GLFWMouseImplementation;
import com.teamti.timc.utils.lwjgl3.input.CombinedInputImplementation;

/**
 * @author Zarzelcow
 * @created 28/09/2022 - 3:12 PM
 */
public class LWJGLImplementationUtils {
    private static InputImplementation _inputImplementation;

    public static InputImplementation getOrCreateInputImplementation() {
        if (_inputImplementation == null) {
            _inputImplementation = createImplementation();
        }
        return _inputImplementation;
    }

    private static InputImplementation createImplementation() {
        return new CombinedInputImplementation(new GLFWKeyboardImplementation(), new GLFWMouseImplementation());
    }

}
