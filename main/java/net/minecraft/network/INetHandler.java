package net.minecraft.network;

import net.minecraft.util.IChatComponent;
import org.lwjgl.LWJGLException;

public interface INetHandler
{
    /**
     * Invoked when disconnecting, the parameter is a ChatComponent describing the reason for termination
     */
    void onDisconnect(IChatComponent reason) throws LWJGLException;
}
