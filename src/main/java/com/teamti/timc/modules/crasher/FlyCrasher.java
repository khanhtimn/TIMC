package com.teamti.timc.modules.crasher;

import com.teamti.timc.event.EventManager;
import com.teamti.timc.main.TIMC;
import com.teamti.timc.main.Module;
import com.teamti.timc.util.crasher.CrasherUtil;
import org.lwjgl.input.Keyboard;

public class FlyCrasher extends Module {


    public static boolean isEnabled;
    private int time = 0;


    public FlyCrasher() {
        super("FlyCrasher", Keyboard.KEY_NONE);
        this.jelloCat = TIMC.tabgui.cats.get(2);
    }

    public void onEnable() {
        isEnabled = true;
        EventManager.register(this);
    }

    public void onDisable() {
        EventManager.unregister(this);
        isEnabled = false;

    }


    public void onUpdate() {
        if (isEnabled) {

            if (!this.mc.thePlayer.capabilities.allowFlying) {
                TIMC.addChatMessage("You must be allowed to fly on the server!");
                toggle();
            } else {

                if (time != 300) {
                    time = time + 1;
                } else {
                    toggle();
                    time = 0;
                }

                CrasherUtil.INSTANCE.FlyCrasher();

            }



            /*
            else  if (mode == CrasherType.CustomPayLoadCrasher_I) {
                CrasherUtil.INSTANCE.CustomPayLoadCrasher_I();
            }
            else  if (mode == CrasherType.CustomPayLoadCrasher_II) {
                CrasherUtil.INSTANCE.CustomPayLoadCrasher_II();
            }
            else  if (mode == CrasherType.CustomPayLoadCrasher_III) {
                CrasherUtil.INSTANCE.CustomPayLoadCrasher_III();
            }
            else  if (mode == CrasherType.CustomPayLoadCrasher_IV) {
                CrasherUtil.INSTANCE.CustomPayLoadCrasher_IV();
            }
            else  if (mode == CrasherType.CustomPayLoadCrasherBypess_I) {
                CrasherUtil.INSTANCE.CustomPayLoadCrasherBypess_I();
            }
            else  if (mode == CrasherType.CustomPayLoadCrasherBypess_II) {
                CrasherUtil.INSTANCE.CustomPayLoadCrasherBypess_II();
            }
            else if (mode == CrasherType.CustomPayLoadCrasherBypess_III) {
                CrasherUtil.INSTANCE.CustomPayLoadCrasherBypess_III();
            }
            else if (mode == CrasherType.GrieferGamesCrasher) {
                CrasherUtil.INSTANCE.GrieferGamesCrasher();
            }
            else if (mode == CrasherType.MapCrasher) {
                CrasherUtil.INSTANCE.MapCrasher();
            }
            else if (mode == CrasherType.NettyCrasher_I) {
                CrasherUtil.INSTANCE.NettyCrasher_I();
            }
            else if (mode == CrasherType.NettyCrasher_II) {
                CrasherUtil.INSTANCE.NettyCrasher_II();
            }
            else if (mode == CrasherType.NettyCrasher_III) {
                CrasherUtil.INSTANCE.NettyCrasher_III();
            }
            else if (mode == CrasherType.NettyCrasher_IV) {
                CrasherUtil.INSTANCE.NettyCrasher_IV();
            }
            else if (mode == CrasherType.OldGommeCrasher) {
                CrasherUtil.INSTANCE.OldGommeCrasher();
            }
            else if (mode == CrasherType.onePacketCrasherBookEdit) {
                CrasherUtil.INSTANCE.onePacketCrasherBookEdit();
            }
            else if (mode == CrasherType.onePacketCrasherBookSign) {
                CrasherUtil.INSTANCE.onePacketCrasherBookSign();
            }
            else if (mode == CrasherType.PacketSpammerCrasher) {
                CrasherUtil.INSTANCE.PacketSpammerCrasher();
            }
            else if (mode == CrasherType.onePlaceCrasher) {
                CrasherUtil.INSTANCE.onePlaceCrasher();
            }
            else if (mode == CrasherType.onePlaceCrasher_Hard) {
                CrasherUtil.INSTANCE.onePlaceHardCrasher();
            }
            else if (mode == CrasherType.oneClickCrasher_Hard) {
                CrasherUtil.INSTANCE.oneClickHardCrasher();
            }
            else if (mode == CrasherType.oneClickCrasher) {
                CrasherUtil.INSTANCE.oneClickCrasher();
            }
            else if (mode == CrasherType.LabyModCrasher) {
                CrasherUtil.INSTANCE.LabyModCrasher();
            }
            else if (mode == CrasherType.CommandBlockCrasher) {
                CrasherUtil.INSTANCE.CommandBlockCrasher();
            }
            else if (mode == CrasherType.GameSettingsCrasher) {
                CrasherUtil.INSTANCE.GameSettingsCrasher();
            }
            else if (mode == CrasherType.AnimationCrasher) {
                CrasherUtil.INSTANCE.AnimationCrasher();
            }
            else if (mode == CrasherType.ItemChancheCrasher) {
                CrasherUtil.INSTANCE.ItemChancheCrasher();
            }
            else if (mode == CrasherType.AACCrasher_I) {
                CrasherUtil.INSTANCE.AACCrasher_I();
            }
            else if (mode == CrasherType.AACCrasher_II) {
                CrasherUtil.INSTANCE.AACCrasher_II();
            }
            else if (mode == CrasherType.AACCrasher_III) {
                CrasherUtil.INSTANCE.AACCrasher_III();
            }
            else if (mode == CrasherType.CubeCraftCrasher) {
                CrasherUtil.INSTANCE.CubeCraftCrasher();
            }
            else if (mode == CrasherType.ChuckLoadingCrasher) {
                CrasherUtil.INSTANCE.ChunkLoadingCrasher();
            }
            else if (mode == CrasherType.BlockClickCrasher) {
                CrasherUtil.INSTANCE.BlockClickCrasher();
            }
            else if (mode == CrasherType.BlockPlaceCrasher) {
                CrasherUtil.INSTANCE.BlockPlaceCrasher();
            }
            else if (mode == CrasherType.RespawnCrasher) {
                CrasherUtil.INSTANCE.RespawnCrasher();
            }
            else if (mode == CrasherType.HopCrasher) {
                CrasherUtil.INSTANCE.HopCrasher();
            }
            else if (mode == CrasherType.BungeeCrasher) {
                CrasherUtil.INSTANCE.BungeeCrasher();
            }
            else if (mode == CrasherType.SuicideCrasher) {
                CrasherUtil.INSTANCE.SuicideCrasher();
            }
            else if (mode == CrasherType.PacketKeepAliveCrasher) {
                CrasherUtil.INSTANCE.PacketKeepAliveCrasher();
            }
            else if (mode == CrasherType.NullPingCrasher) {
                CrasherUtil.INSTANCE.NullPingCrasher();
            }
            else if (mode == CrasherType.BookFloodCrasher) {
                CrasherUtil.INSTANCE.BookFloodCrasher();
            }
            else if (mode == CrasherType.MultiCrasher) {
                CrasherUtil.INSTANCE.MultiCrasher();
            }
            else if (mode == CrasherType.ClickCrasher) {
                CrasherUtil.INSTANCE.ClickCrasher();
            }
            else if (mode == CrasherType.PlaceBookCrasher) {
                CrasherUtil.INSTANCE.PlaceBookCrasher();
            }
            else if (mode == CrasherType.AutoCompleteMixed) {
                CrasherUtil.INSTANCE.AutoCompleteMixed();
            }
            else if (mode == CrasherType.AutoCompletePlayer) {
                CrasherUtil.INSTANCE.AutoCompletePlayer();
            }
            else if (mode == CrasherType.AutoCompleteCommand) {
                CrasherUtil.INSTANCE.AutoCompleteCommand();
            }
            else if (mode == CrasherType.giveSkullCrasher) {
                CrasherUtil.INSTANCE.giveSkullCrasher();
            }
            else if (mode == CrasherType.setBookCrasher) {
                CrasherUtil.INSTANCE.setBookCrasher();
            }
            else if (mode == CrasherType.TestCrasher) {
                CrasherUtil.INSTANCE.TestCrasher();
            }
            else if (mode == CrasherType.ExploitFixerCrasher) {
                CrasherUtil.INSTANCE.ExploitFixerCrasher();
            }
            else if (mode == CrasherType.MoveCrasher) {
                CrasherUtil.INSTANCE.MoveCrasher();
            }
            else if (mode == CrasherType.AnimCrasher2) {
                CrasherUtil.INSTANCE.AnimCrasher2();
            }
            else if (mode == CrasherType.BypassCrasher) {
                CrasherUtil.INSTANCE.BypassCrasher();
            }
            else if (mode == CrasherType.NormalCrasher) {
                CrasherUtil.INSTANCE.NormalCrasher();
            }
            else if (mode == CrasherType.OnePacketEdited) {
                CrasherUtil.INSTANCE.OnePacketEdited();
            }
            else if (mode == CrasherType.PickupCrasher) {
                CrasherUtil.INSTANCE.PickupCrasher();
            }
            else if (mode == CrasherType.SwingCrasher) {
                CrasherUtil.INSTANCE.SwingCrasher();
            }
            else if (mode == CrasherType.OnGroundCrasher) {
                CrasherUtil.INSTANCE.OnGroundCrasher();
            }
            else if (mode == CrasherType.LabySpammerCrasher) {
                CrasherUtil.INSTANCE.LabySpammerCrasher();
            }
            else if (mode == CrasherType.LabyJoinerCrasher) {
                CrasherUtil.INSTANCE.LabyJoinerCrasher();
            }
            else if (mode == CrasherType.ExceptionerCrasher) {
                CrasherUtil.INSTANCE.ExceptionerCrasher();
            }
            else if (mode == CrasherType.DoubleClickCrasher) {
                CrasherUtil.INSTANCE.DoubleClickCrasher();
            }
            else if (mode == CrasherType.ColorCrasher) {
                CrasherUtil.INSTANCE.ColorCrasher();
            }
            else if (mode == CrasherType.SmashDashCrasher) {
                CrasherUtil.INSTANCE.SmashDashCrasher();
            }
            else if (mode == CrasherType.MEFCrasher) {
                CrasherUtil.INSTANCE.MEFCrasher();
            }
            else if (mode == CrasherType.ZeroSmasherCrasher) {
                CrasherUtil.INSTANCE.ZeroSmasherCrasher();
            }
            else if (mode == CrasherType.BookBypass1) {
                CrasherUtil.INSTANCE.BookBypass1();
            }
            else if (mode == CrasherType.BookBypass2) {
                CrasherUtil.INSTANCE.BookBypass2();
            }
            else if (mode == CrasherType.PositionCrasher) {
                CrasherUtil.INSTANCE.PositionCrasher();
             */


            }
        }

    }

