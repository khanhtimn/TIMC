/*
 * Decompiled with CFR 0.150.
 */
package com.teamti.timc.util.respack;

import com.teamti.timc.util.respack.render.RenderPackListOverlay;

public final class ResourcePackOrganizer {
    public static final String MODID = "ResourcePackOrganizer";
    public static final String MODNAME = "Resource Pack Organizer";
    private ResourcePackOrganizer instance = this;

    public ResourcePackOrganizer() {
        this.onBert();
    }

    public void onBert() {
        RenderPackListOverlay.refreshPackNames();
    }
}

