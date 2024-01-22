/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 */
package com.teamti.timc.util.respack.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ResourcePackRepositoryCustom
        extends ResourcePackRepository {
    private static Constructor<ResourcePackRepository.Entry> entryConstructor;
    private List<ResourcePackRepository.Entry> repositoryEntriesAll = Lists.newArrayList();
    private List<ResourcePackRepository.Entry> repositoryEntries = Lists.newArrayList();
    private boolean isReady = true;

    public static void overrideRepository(List<String> enabledPacks) {
        Minecraft mc = Minecraft.getMinecraft();
        Field fieldRepository = ReflectionHelper.findField(Minecraft.class, "mcResourcePackRepository", "mcResourcePackRepository");
        File fileResourcepacks = (File)ReflectionHelper.getPrivateValue(Minecraft.class, mc, "fileResourcepacks", "fileResourcepacks");
        try {
            ResourcePackRepository originalRepo = (ResourcePackRepository)fieldRepository.get(mc);
            fieldRepository.set(mc, new ResourcePackRepositoryCustom(fileResourcepacks, new File(mc.mcDataDir, "server-resource-packs"), mc.mcDefaultResourcePack, originalRepo.rprMetadataSerializer, mc.gameSettings, enabledPacks));
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static ResourcePackRepository.Entry createEntryInstance(ResourcePackRepository repository, File file) {
        try {
            if (entryConstructor == null) {
                entryConstructor = ResourcePackRepository.Entry.class.getDeclaredConstructor(ResourcePackRepository.class, File.class);
                entryConstructor.setAccessible(true);
            }
            return entryConstructor.newInstance(repository, file);
        }
        catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public ResourcePackRepositoryCustom(File dirResourcepacks, File dirServerResourcepacks, IResourcePack rprDefaultResourcePack, IMetadataSerializer rprMetadataSerializer, GameSettings settings, List<String> enabledPacks) {
        super(dirResourcepacks, dirServerResourcepacks, rprDefaultResourcePack, rprMetadataSerializer, settings);
        this.updateRepositoryEntriesAll();
        for (String pack : enabledPacks) {
            for (ResourcePackRepository.Entry entry : this.repositoryEntriesAll) {
                if (!entry.getResourcePackName().equals(pack)) continue;
                this.repositoryEntries.add(entry);
            }
        }
    }

    private final List<File> getResourcePackFiles(File root) {
        if (root.isDirectory()) {
            ArrayList packFiles = Lists.newArrayList();
            for (File file : root.listFiles()) {
                if (file.isDirectory() && !new File(file, "pack.mcmeta").isFile()) {
                    packFiles.addAll(this.getResourcePackFiles(file));
                    continue;
                }
                packFiles.add(file);
            }
            return packFiles;
        }
        return Collections.emptyList();
    }

    @Override
    public void updateRepositoryEntriesAll() {
        if (!this.isReady) {
            return;
        }
        ArrayList list = Lists.newArrayList();
        for (File file : this.getResourcePackFiles(this.getDirResourcepacks())) {
            ResourcePackRepository.Entry entry = ResourcePackRepositoryCustom.createEntryInstance(this, file);
            if (!this.repositoryEntriesAll.contains(entry)) {
                try {
                    entry.updateResourcePack();
                    list.add(entry);
                }
                catch (Exception e) {
                    list.remove(entry);
                }
                continue;
            }
            int index = this.repositoryEntriesAll.indexOf(entry);
            if (index <= -1 || index >= this.repositoryEntriesAll.size()) continue;
            list.add(this.repositoryEntriesAll.get(index));
        }
        this.repositoryEntriesAll.removeAll(list);
        for (ResourcePackRepository.Entry entry : this.repositoryEntriesAll) {
            entry.closeResourcePack();
        }
        this.repositoryEntriesAll = list;
    }

    @Override
    public List<ResourcePackRepository.Entry> getRepositoryEntriesAll() {
        return ImmutableList.copyOf(this.repositoryEntriesAll);
    }

    @Override
    public List<ResourcePackRepository.Entry> getRepositoryEntries() {
        return ImmutableList.copyOf(this.repositoryEntries);
    }

    @Override
    public void setRepositories(List<ResourcePackRepository.Entry> repositories) {
        this.repositoryEntries.clear();
        this.repositoryEntries.addAll(repositories);
    }
}

