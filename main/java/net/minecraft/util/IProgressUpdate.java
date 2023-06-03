package net.minecraft.util;

import org.lwjgl.LWJGLException;

public interface IProgressUpdate
{
    /**
     * Shows the 'Saving level' string.
     */
    void displaySavingString(String message);

    /**
     * this string, followed by "working..." and then the "% complete" are the 3 lines shown. This resets progress to 0,
     * and the WorkingString to "working...".
     */
    void resetProgressAndMessage(String message);

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    void displayLoadingString(String message) throws LWJGLException;

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    void setLoadingProgress(int progress) throws LWJGLException;

    void setDoneWorking();
}
