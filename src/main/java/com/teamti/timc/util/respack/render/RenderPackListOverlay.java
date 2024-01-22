/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package com.teamti.timc.util.respack.render;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.ResourcePackRepository;

import java.util.ArrayList;
import java.util.List;

public final class RenderPackListOverlay {
    private static final RenderPackListOverlay instance = new RenderPackListOverlay();
    private static boolean isRegistered;
    private List<String> packNames = new ArrayList<String>(4);

    public static void register() {
        if (isRegistered) {
            return;
        }
        isRegistered = true;
        RenderPackListOverlay.refreshPackNames();
    }

    public static void unregister() {
        if (!isRegistered) {
            return;
        }
        isRegistered = false;
    }

    public static void refreshPackNames() {
        instance.refresh();
    }

    private void refresh() {
        this.packNames.clear();
        List<ResourcePackRepository.Entry> entries = Lists.reverse(Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries());
        for (ResourcePackRepository.Entry entry : entries) {
            String name = entry.getResourcePackName();
            if (name.endsWith(".zip")) {
                name = name.substring(0, name.length() - 4);
            }
            this.packNames.add(name);
        }
    }

    public void onRenderGameOverlay() {
    }

    private static void renderText(FontRenderer renderer, String line, int x, int y, int color, boolean alignRight) {
        renderer.func_175065_a(line, alignRight ? (float)(x - renderer.getStringWidth(line)) : (float)x, y, color, color != 0);
    }
}

