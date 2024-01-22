/*
 * Decompiled with CFR 0.150.
 */
package com.teamti.timc.util.respack.packs;

import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryFound;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class ResourcePackListProcessor {
    public static final Comparator<ResourcePackListEntry> sortAZ = new Comparator<ResourcePackListEntry>(){

        @Override
        public int compare(ResourcePackListEntry entry1, ResourcePackListEntry entry2) {
            return String.CASE_INSENSITIVE_ORDER.compare(ResourcePackListProcessor.nameSort(entry1, false), ResourcePackListProcessor.nameSort(entry2, false));
        }
    };
    public static final Comparator<ResourcePackListEntry> sortZA = new Comparator<ResourcePackListEntry>(){

        @Override
        public int compare(ResourcePackListEntry entry1, ResourcePackListEntry entry2) {
            return -String.CASE_INSENSITIVE_ORDER.compare(ResourcePackListProcessor.nameSort(entry1, true), ResourcePackListProcessor.nameSort(entry2, true));
        }
    };
    private final List<ResourcePackListEntry> sourceList;
    private final List<ResourcePackListEntry> targetList;
    private Comparator<ResourcePackListEntry> sorter;
    private Pattern textFilter;

    private static String name(ResourcePackListEntry entry) {
        if (entry instanceof ResourcePackListEntryCustom) {
            return ((ResourcePackListEntryCustom)entry).func_148312_b();
        }
        if (entry instanceof ResourcePackListEntryFound) {
            return ((ResourcePackListEntryFound)entry).func_148318_i().getResourcePackName();
        }
        return "<INVALID>";
    }

    private static String nameSort(ResourcePackListEntry entry, boolean reverse) {
        String pfx3;
        String pfx1 = !reverse ? "a" : "z";
        String pfx2 = !reverse ? "b" : "z";
        String string = pfx3 = !reverse ? "z" : "a";
        if (entry instanceof ResourcePackListEntryFolder) {
            ResourcePackListEntryFolder folder = (ResourcePackListEntryFolder)entry;
            return folder.isUp ? pfx1 + folder.folderName : pfx2 + folder.folderName;
        }
        if (entry instanceof ResourcePackListEntryCustom) {
            return pfx3 + ((ResourcePackListEntryCustom)entry).func_148312_b();
        }
        if (entry instanceof ResourcePackListEntryFound) {
            return pfx3 + ((ResourcePackListEntryFound)entry).func_148318_i().getResourcePackName();
        }
        return pfx3 + "<INVALID>";
    }

    private static String description(ResourcePackListEntry entry) {
        if (entry instanceof ResourcePackListEntryCustom) {
            return ((ResourcePackListEntryCustom)entry).func_148311_a();
        }
        if (entry instanceof ResourcePackListEntryFound) {
            return ((ResourcePackListEntryFound)entry).func_148318_i().getTexturePackDescription();
        }
        return "<INVALID>";
    }

    public ResourcePackListProcessor(List<ResourcePackListEntry> sourceList, List<ResourcePackListEntry> targetList) {
        this.sourceList = sourceList;
        this.targetList = targetList;
        this.refresh();
    }

    public void setSorter(Comparator<ResourcePackListEntry> comparator) {
        this.sorter = comparator;
        this.refresh();
    }

    public void setFilter(String text) {
        this.textFilter = text == null || text.isEmpty() ? null : Pattern.compile("\\Q" + text.replace("*", "\\E.*\\Q") + "\\E", 2);
        this.refresh();
    }

    public void refresh() {
        this.targetList.clear();
        for (ResourcePackListEntry entry : this.sourceList) {
            if (!this.checkFilter(ResourcePackListProcessor.name(entry)) && !this.checkFilter(ResourcePackListProcessor.description(entry))) continue;
            this.targetList.add(entry);
        }
        if (this.sorter != null) {
            Collections.sort(this.targetList, this.sorter);
        }
    }

    private boolean checkFilter(String entryText) {
        return this.textFilter == null || this.textFilter.matcher(entryText.toLowerCase(Locale.ENGLISH)).find();
    }
}

