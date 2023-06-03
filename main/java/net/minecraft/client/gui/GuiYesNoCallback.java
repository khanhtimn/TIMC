package net.minecraft.client.gui;

import org.lwjgl.LWJGLException;

public interface GuiYesNoCallback
{
    void confirmClicked(boolean result, int id) throws LWJGLException;
}
