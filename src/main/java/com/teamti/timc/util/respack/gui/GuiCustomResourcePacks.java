/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.lwjgl.input.Keyboard
 */
package com.teamti.timc.util.respack.gui;

import com.teamti.timc.util.respack.packs.ResourcePackListEntryFolder;
import com.teamti.timc.util.respack.packs.ResourcePackListProcessor;
import com.teamti.timc.util.respack.render.RenderPackListOverlay;
import com.teamti.timc.util.respack.repository.ResourcePackRepositoryCustom;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiResourcePackAvailable;
import net.minecraft.client.gui.GuiResourcePackSelected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryDefault;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;
import org.lwjgl.input.Keyboard;

public class GuiCustomResourcePacks
        extends GuiScreenResourcePacks {
    private final GuiScreen parentScreen;
    private GuiTextField searchField;
    private GuiResourcePackAvailable guiPacksAvailable;
    private GuiResourcePackSelected guiPacksSelected;
    private List<ResourcePackListEntry> listPacksAvailable;
    private List<ResourcePackListEntry> listPacksAvailableProcessed;
    private List<ResourcePackListEntry> listPacksDummy;
    private List<ResourcePackListEntry> listPacksSelected;
    private ResourcePackListProcessor listProcessor;
    private File currentFolder;
    private GuiButton selectedButton;
    private boolean hasUpdated;
    private boolean requiresReload;
    private Comparator<ResourcePackListEntry> currentSorter;

    public GuiCustomResourcePacks(GuiScreen parentScreen) {
        super(parentScreen);
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 100 - 75, this.height - 26, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiOptionButton(2, this.width / 2 + 100 - 75, this.height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
        this.buttonList.add(new GuiOptionButton(10, this.width / 2 - 204, this.height - 26, 40, 20, "A-Z"));
        this.buttonList.add(new GuiOptionButton(11, this.width / 2 - 204 + 44, this.height - 26, 40, 20, "Z-A"));
        this.buttonList.add(new GuiOptionButton(20, this.width / 2 - 74, this.height - 26, 70, 20, "Refresh"));
        String prevText = this.searchField == null ? "" : this.searchField.getText();
        this.searchField = new GuiTextField(30, this.fontRendererObj, this.width / 2 - 203, this.height - 46, 198, 16);
        this.searchField.setText(prevText);
        if (!this.requiresReload) {
            this.listPacksAvailable = Lists.newArrayListWithCapacity((int)8);
            this.listPacksAvailableProcessed = Lists.newArrayListWithCapacity((int)8);
            this.listPacksDummy = Lists.newArrayListWithCapacity((int)1);
            this.listPacksSelected = Lists.newArrayListWithCapacity((int)8);
            ResourcePackRepository repository = this.mc.getResourcePackRepository();
            repository.updateRepositoryEntriesAll();
            this.currentFolder = repository.getDirResourcepacks();
            this.listPacksAvailable.addAll(this.createAvailablePackList(repository));
            for (ResourcePackRepository.Entry entry : Lists.reverse(repository.getRepositoryEntries())) {
                this.listPacksSelected.add(new ResourcePackListEntryFound(this, entry));
            }
            this.listPacksSelected.add(new ResourcePackListEntryDefault(this));
        }
        this.guiPacksAvailable = new GuiResourcePackAvailable(this.mc, 200, this.height, this.listPacksAvailableProcessed);
        this.guiPacksAvailable.setSlotXBoundsFromLeft(this.width / 2 - 204);
        this.guiPacksAvailable.registerScrollButtons(7, 8);
        this.guiPacksAvailable.top = 4;
        this.guiPacksSelected = new GuiResourcePackSelected(this.mc, 200, this.height, this.listPacksSelected);
        this.guiPacksSelected.setSlotXBoundsFromLeft(this.width / 2 + 4);
        this.guiPacksSelected.registerScrollButtons(7, 8);
        this.guiPacksSelected.top = 4;
        this.listProcessor = new ResourcePackListProcessor(this.listPacksAvailable, this.listPacksAvailableProcessed);
        this.listProcessor.setSorter(this.currentSorter == null ? (this.currentSorter = ResourcePackListProcessor.sortAZ) : this.currentSorter);
        this.listProcessor.setFilter(this.searchField.getText().trim());
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 20) {
            this.refreshAvailablePacks();
        } else if (button.id == 11) {
            this.currentSorter = ResourcePackListProcessor.sortZA;
            this.listProcessor.setSorter(this.currentSorter);
        } else if (button.id == 10) {
            this.currentSorter = ResourcePackListProcessor.sortAZ;
            this.listProcessor.setSorter(this.currentSorter);
        } else if (button.id == 2) {
            GuiUtils.openFolder(this.mc.getResourcePackRepository().getDirResourcepacks());
        } else if (button.id == 1) {
            if (this.requiresReload) {
                List<ResourcePackRepository.Entry> selected = this.refreshSelectedPacks();
                this.mc.gameSettings.resourcePacks.clear();
                for (ResourcePackRepository.Entry entry : selected) {
                    this.mc.gameSettings.resourcePacks.add(entry.getResourcePackName());
                }
                this.mc.gameSettings.saveOptions();
                this.mc.refreshResources();
                RenderPackListOverlay.refreshPackNames();
            }
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int buttonId) {
        if (buttonId == 0) {
            for (GuiButton button : this.buttonList) {
                if (!button.mousePressed(this.mc, mouseX, mouseY)) continue;
                this.selectedButton = button;
                button.playPressSound(this.mc.getSoundHandler());
                this.actionPerformed(button);
            }
        }
        this.guiPacksAvailable.func_148179_a(mouseX, mouseY, buttonId);
        this.guiPacksSelected.func_148179_a(mouseX, mouseY, buttonId);
        this.searchField.mouseClicked(mouseX, mouseY, buttonId);
        this.listProcessor.refresh();
    }

    @Override
    public void handleMouseInput() throws IOException {
        try {
            super.handleMouseInput();
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
        this.guiPacksAvailable.func_178039_p();
        this.guiPacksSelected.func_178039_p();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int eventType) {
        if (eventType == 0 && this.selectedButton != null) {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
    }

    @Override
    protected void keyTyped(char keyChar, int keyCode) throws IOException {
        super.keyTyped(keyChar, keyCode);
        if (this.searchField.isFocused()) {
            this.searchField.textboxKeyTyped(keyChar, keyCode);
            this.listProcessor.setFilter(this.searchField.getText().trim());
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    public void updateScreen() {
        this.searchField.updateCursorCounter();
        if (this.hasUpdated) {
            this.hasUpdated = false;
            this.refreshSelectedPacks();
            this.refreshAvailablePacks();
        }
    }

    public void moveToFolder(File folder) {
        this.currentFolder = folder;
        this.refreshSelectedPacks();
        this.refreshAvailablePacks();
    }

    public void refreshAvailablePacks() {
        this.listPacksAvailable.clear();
        this.listPacksAvailable.addAll(this.createAvailablePackList(this.mc.getResourcePackRepository()));
        this.listProcessor.refresh();
    }

    public List<ResourcePackRepository.Entry> refreshSelectedPacks() {
        ArrayList selected = Lists.newArrayListWithCapacity((int)this.listPacksSelected.size());
        for (ResourcePackListEntry entry : this.listPacksSelected) {
            ResourcePackListEntryFound packEntry;
            if (!(entry instanceof ResourcePackListEntryFound) || (packEntry = (ResourcePackListEntryFound)entry).func_148318_i() == null) continue;
            selected.add(packEntry.func_148318_i());
        }
        Collections.reverse(selected);
        this.mc.getResourcePackRepository().setRepositories(selected);
        return selected;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTickTime) {
        this.drawDefaultBackground();
        this.guiPacksAvailable.drawScreen(mouseX, mouseY, partialTickTime);
        this.guiPacksSelected.drawScreen(mouseX, mouseY, partialTickTime);
        this.searchField.drawTextBox();
        for (GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }
    }

    private List<ResourcePackListEntryFound> createAvailablePackList(ResourcePackRepository repository) {
        File[] files;
        ArrayList list = Lists.newArrayList();
        if (!repository.getDirResourcepacks().equals(this.currentFolder)) {
            list.add(new ResourcePackListEntryFolder(this, this.currentFolder.getParentFile(), true));
        }
        if ((files = this.currentFolder.listFiles()) != null) {
            for (File file : files) {
                if (file.isDirectory() && !new File(file, "pack.mcmeta").isFile()) {
                    list.add(new ResourcePackListEntryFolder(this, file));
                    continue;
                }
                ResourcePackRepository.Entry entry = ResourcePackRepositoryCustom.createEntryInstance(repository, file);
                if (entry == null) continue;
                try {
                    entry.updateResourcePack();
                    list.add(new ResourcePackListEntryFound(this, entry));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        List<ResourcePackRepository.Entry> repositoryEntries = repository.getRepositoryEntries();
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            ResourcePackListEntryFound listEntry = (ResourcePackListEntryFound)iter.next();
            if (listEntry.func_148318_i() == null || !repositoryEntries.contains(listEntry.func_148318_i())) continue;
            iter.remove();
        }
        return list;
    }

    @Override
    public boolean hasResourcePackEntry(ResourcePackListEntry entry) {
        return this.listPacksSelected.contains(entry);
    }

    public List getListContaining(ResourcePackListEntry entry) {
        return this.hasResourcePackEntry(entry) ? this.listPacksSelected : this.listPacksAvailable;
    }

    @Override
    public List<ResourcePackListEntry> getAvailableResourcePacks() {
        this.hasUpdated = true;
        this.listPacksDummy.clear();
        return this.listPacksDummy;
    }

    @Override
    public List<ResourcePackListEntry> getSelectedResourcePacks() {
        this.hasUpdated = true;
        return this.listPacksSelected;
    }

    @Override
    public void markChanged() {
        this.requiresReload = true;
    }
}

