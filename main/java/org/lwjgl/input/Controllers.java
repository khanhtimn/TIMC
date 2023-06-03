/*
 * Copyright (c) 2002-2008 LWJGL Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.lwjgl.input;

import org.lwjgl.LWJGLException;

/**
 * The collection of controllers currently connected.
 *
 * @author Kevin Glass
 */
public class Controllers {

    public static void create() throws LWJGLException {
    }

    public static int getControllerCount() {
        return 0;
    }

    public static void poll() {
    }

    public static void clearEvents() {
    }

    public static boolean next() {
        return false;
    }

    public static boolean isCreated() {
        return false;
    }

    public static void destroy() {
    }

    public static int getEventControlIndex() {
        return 0;
    }

    public static boolean isEventButton() {
        return false;
    }

    public static boolean isEventAxis() {
        return false;
    }

    public static boolean isEventXAxis() {
        return false;
    }

    public static boolean isEventYAxis() {
        return false;
    }

    public static boolean isEventPovX() {
        return false;
    }

    public static boolean isEventPovY() {
        return false;
    }

    public static long getEventNanoseconds() {
        return 0;
    }

    public static boolean getEventButtonState() {
        return false;
    }

    public static float getEventXAxisValue() {
        return 0;
    }

    public static float getEventYAxisValue() {
        return 0;
    }
}
