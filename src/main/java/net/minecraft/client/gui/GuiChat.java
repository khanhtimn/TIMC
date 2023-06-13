//package net.minecraft.client.gui;
//
//import com.google.common.collect.Lists;
//import java.io.IOException;
//import java.util.List;
//import net.minecraft.network.play.client.C14PacketTabComplete;
//import net.minecraft.util.BlockPos;
//import net.minecraft.util.ChatComponentText;
//import net.minecraft.util.IChatComponent;
//import net.minecraft.util.MathHelper;
//import net.minecraft.util.MovingObjectPosition;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.lwjgl.input.Keyboard;
//import org.lwjgl.input.Mouse;
//
//public class GuiChat extends GuiScreen
//{
//    private static final Logger logger = LogManager.getLogger();
//    private String historyBuffer = "";
//
//    /**
//     * keeps position of which chat message you will select when you press up, (does not increase for duplicated
//     * messages sent immediately after each other)
//     */
//    private int sentHistoryCursor = -1;
//    private boolean playerNamesFound;
//    private boolean waitingOnAutocomplete;
//    private int autocompleteIndex;
//    private List<String> foundPlayerNames = Lists.<String>newArrayList();
//
//    /** Chat entry field */
//    protected GuiTextField inputField;
//
//    /**
//     * is the text that appears when you press the chat key and the input box appears pre-filled
//     */
//    private String defaultInputFieldText = "";
//
//    public GuiChat()
//    {
//    }
//
//    public GuiChat(String defaultText)
//    {
//        this.defaultInputFieldText = defaultText;
//    }
//
//    /**
//     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
//     * window resizes, the buttonList is cleared beforehand.
//     */
//    public void initGui()
//    {
//        Keyboard.enableRepeatEvents(true);
//        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
//        this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
//        this.inputField.setMaxStringLength(100);
//        this.inputField.setEnableBackgroundDrawing(false);
//        this.inputField.setFocused(true);
//        this.inputField.setText(this.defaultInputFieldText);
//        this.inputField.setCanLoseFocus(false);
//    }
//
//    /**
//     * Called when the screen is unloaded. Used to disable keyboard repeat events
//     */
//    public void onGuiClosed()
//    {
//        Keyboard.enableRepeatEvents(false);
//        this.mc.ingameGUI.getChatGUI().resetScroll();
//    }
//
//    /**
//     * Called from the main game loop to update the screen.
//     */
//    public void updateScreen()
//    {
//        this.inputField.updateCursorCounter();
//    }
//
//    /**
//     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
//     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
//     */
//    protected void keyTyped(char typedChar, int keyCode) throws IOException
//    {
//        this.waitingOnAutocomplete = false;
//
//        if (keyCode == 15)
//        {
//            this.autocompletePlayerNames();
//        }
//        else
//        {
//            this.playerNamesFound = false;
//        }
//
//        if (keyCode == 1)
//        {
//            this.mc.displayGuiScreen((GuiScreen)null);
//        }
//        else if (keyCode != 28 && keyCode != 156)
//        {
//            if (keyCode == 200)
//            {
//                this.getSentHistory(-1);
//            }
//            else if (keyCode == 208)
//            {
//                this.getSentHistory(1);
//            }
//            else if (keyCode == 201)
//            {
//                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
//            }
//            else if (keyCode == 209)
//            {
//                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
//            }
//            else
//            {
//                this.inputField.textboxKeyTyped(typedChar, keyCode);
//            }
//        }
//        else
//        {
//            String s = this.inputField.getText().trim();
//
//            if (s.length() > 0)
//            {
//                this.sendChatMessage(s);
//            }
//
//            this.mc.displayGuiScreen((GuiScreen)null);
//        }
//    }
//
//    /**
//     * Handles mouse input.
//     */
//    public void handleMouseInput() throws IOException
//    {
//        super.handleMouseInput();
//        int i = Mouse.getEventDWheel();
//
//        if (i != 0)
//        {
//            if (i > 1)
//            {
//                i = 1;
//            }
//
//            if (i < -1)
//            {
//                i = -1;
//            }
//
//            if (!isShiftKeyDown())
//            {
//                i *= 7;
//            }
//
//            this.mc.ingameGUI.getChatGUI().scroll(i);
//        }
//    }
//
//    /**
//     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
//     */
//    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
//    {
//        if (mouseButton == 0)
//        {
//            IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
//
//            if (this.handleComponentClick(ichatcomponent))
//            {
//                return;
//            }
//        }
//
//        this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
//        super.mouseClicked(mouseX, mouseY, mouseButton);
//    }
//
//    /**
//     * Sets the text of the chat
//     */
//    protected void setText(String newChatText, boolean shouldOverwrite)
//    {
//        if (shouldOverwrite)
//        {
//            this.inputField.setText(newChatText);
//        }
//        else
//        {
//            this.inputField.writeText(newChatText);
//        }
//    }
//
//    public void autocompletePlayerNames()
//    {
//        if (this.playerNamesFound)
//        {
//            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
//
//            if (this.autocompleteIndex >= this.foundPlayerNames.size())
//            {
//                this.autocompleteIndex = 0;
//            }
//        }
//        else
//        {
//            int i = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
//            this.foundPlayerNames.clear();
//            this.autocompleteIndex = 0;
//            String s = this.inputField.getText().substring(i).toLowerCase();
//            String s1 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
//            this.sendAutocompleteRequest(s1, s);
//
//            if (this.foundPlayerNames.isEmpty())
//            {
//                return;
//            }
//
//            this.playerNamesFound = true;
//            this.inputField.deleteFromCursor(i - this.inputField.getCursorPosition());
//        }
//
//        if (this.foundPlayerNames.size() > 1)
//        {
//            StringBuilder stringbuilder = new StringBuilder();
//
//            for (String s2 : this.foundPlayerNames)
//            {
//                if (stringbuilder.length() > 0)
//                {
//                    stringbuilder.append(", ");
//                }
//
//                stringbuilder.append(s2);
//            }
//
//            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(stringbuilder.toString()), 1);
//        }
//
//        this.inputField.writeText((String)this.foundPlayerNames.get(this.autocompleteIndex++));
//    }
//
//    private void sendAutocompleteRequest(String p_146405_1_, String p_146405_2_)
//    {
//        if (p_146405_1_.length() >= 1)
//        {
//            BlockPos blockpos = null;
//
//            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
//            {
//                blockpos = this.mc.objectMouseOver.getBlockPos();
//            }
//
//            this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(p_146405_1_, blockpos));
//            this.waitingOnAutocomplete = true;
//        }
//    }
//
//    /**
//     * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
//     * message from the current cursor position
//     */
//    public void getSentHistory(int msgPos)
//    {
//        int i = this.sentHistoryCursor + msgPos;
//        int j = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
//        i = MathHelper.clamp_int(i, 0, j);
//
//        if (i != this.sentHistoryCursor)
//        {
//            if (i == j)
//            {
//                this.sentHistoryCursor = j;
//                this.inputField.setText(this.historyBuffer);
//            }
//            else
//            {
//                if (this.sentHistoryCursor == j)
//                {
//                    this.historyBuffer = this.inputField.getText();
//                }
//
//                this.inputField.setText((String)this.mc.ingameGUI.getChatGUI().getSentMessages().get(i));
//                this.sentHistoryCursor = i;
//            }
//        }
//    }
//
//    /**
//     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
//     */
//    public void drawScreen(int mouseX, int mouseY, float partialTicks)
//    {
//        drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
//        this.inputField.drawTextBox();
//        IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
//
//        if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatHoverEvent() != null)
//        {
//            this.handleComponentHover(ichatcomponent, mouseX, mouseY);
//        }
//
//        super.drawScreen(mouseX, mouseY, partialTicks);
//    }
//
//    public void onAutocompleteResponse(String[] p_146406_1_)
//    {
//        if (this.waitingOnAutocomplete)
//        {
//            this.playerNamesFound = false;
//            this.foundPlayerNames.clear();
//
//            for (String s : p_146406_1_)
//            {
//                if (s.length() > 0)
//                {
//                    this.foundPlayerNames.add(s);
//                }
//            }
//
//            String s1 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
//            String s2 = StringUtils.getCommonPrefix(p_146406_1_);
//
//            if (s2.length() > 0 && !s1.equalsIgnoreCase(s2))
//            {
//                this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
//                this.inputField.writeText(s2);
//            }
//            else if (this.foundPlayerNames.size() > 0)
//            {
//                this.playerNamesFound = true;
//                this.autocompletePlayerNames();
//            }
//        }
//    }
//
//    /**
//     * Returns true if this GUI should pause the game when it is displayed in single-player
//     */
//    public boolean doesGuiPauseGame()
//    {
//        return false;
//    }
//}

package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.teamti.timc.config.DragManager;
import com.teamti.timc.main.TIMC;

import com.teamti.timc.utils.Utils;
import com.teamti.timc.utils.animations.Animation;
import com.teamti.timc.utils.animations.Direction;
import com.teamti.timc.utils.animations.impl.DecelerateAnimation;
import com.teamti.timc.utils.drag.Dragging;
import com.teamti.timc.utils.render.ColorUtil;
import com.teamti.timc.utils.render.RoundedUtil;
import com.teamti.timc.utils.tuples.Pair;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class GuiChat extends GuiScreen {
    private static final Logger logger = LogManager.getLogger();
    private String historyBuffer = "";

    Animation resetButtonHover;

    public static Animation openingAnimation = new DecelerateAnimation(175, 1, Direction.BACKWARDS);

    /**
     * keeps position of which chat message you will select when you press up, (does not increase for duplicated
     * messages sent immediately after each other)
     */
    private int sentHistoryCursor = -1;
    private boolean playerNamesFound;
    private boolean waitingOnAutocomplete;
    private int autocompleteIndex;
    private List<String> foundPlayerNames = Lists.<String>newArrayList();

    /**
     * Chat entry field
     */
    protected GuiTextField inputField;

    /**
     * is the text that appears when you press the chat key and the input box appears pre-filled
     */
    private String defaultInputFieldText = "";

    public GuiChat() {
    }

    public GuiChat(String defaultText) {
        this.defaultInputFieldText = defaultText;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {
        openingAnimation.setDirection(Direction.BACKWARDS);
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.waitingOnAutocomplete = false;

        if (keyCode == 15) {
            this.autocompletePlayerNames();
        } else {
            this.playerNamesFound = false;
        }

        if (keyCode == 1) {
            openingAnimation.setDirection(Direction.BACKWARDS);

        } else if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 200) {
                this.getSentHistory(-1);
            } else if (keyCode == 208) {
                this.getSentHistory(1);
            } else if (keyCode == 201) {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
            } else if (keyCode == 209) {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
            } else {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
            }
        } else {
            String s = this.inputField.getText().trim();

            if (s.length() > 0) {
                this.sendChatMessage(s);
            }
            openingAnimation.setDirection(Direction.BACKWARDS);
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0) {
            if (i > 1) {
                i = 1;
            }

            if (i < -1) {
                i = -1;
            }

            if (!isShiftKeyDown()) {
                i *= 7;
            }

            this.mc.ingameGUI.getChatGUI().scroll(i);
        }
    }



    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {

//        for (Dragging dragging : DragManager.draggables.values()) {
//            if (!dragging.hoverAnimation.getDirection().equals(Direction.BACKWARDS)) {
//                dragging.hoverAnimation.setDirection(Direction.BACKWARDS);
//            }
//        }

        openingAnimation = new DecelerateAnimation(175, 1);


        resetButtonHover = new DecelerateAnimation(250, 1);

        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }


    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (openingAnimation.finished(Direction.BACKWARDS)) {
            this.mc.displayGuiScreen((GuiScreen) null);
            return;
        }

        Gui.drawRect2(2, this.height - (14 * openingAnimation.getOutput().floatValue()), this.width - 4, 12, Integer.MIN_VALUE);

//        AbstractFontRenderer abstractFontRenderer = this.mc.fontRendererObj;
//        if (HUDMod.customFont.isEnabled()) {
//            abstractFontRenderer = FontUtil.tenacityFont20;
//        }
//        inputField.font = abstractFontRenderer;

        inputField.yPosition = (int) (this.height - (12 * openingAnimation.getOutput().floatValue()));
        this.inputField.drawTextBox();
        IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

        if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatHoverEvent() != null) {
            this.handleComponentHover(ichatcomponent, mouseX, mouseY);
        }

        DragManager.draggables.values().forEach(dragging -> {
            if (dragging.getModule().isEnabled()) {
//                if (dragging.getModule().equals(arraylistMod)) {
//                    dragging.onDrawArraylist(arraylistMod, mouseX, mouseY);
//                } else {
//                    dragging.onDraw(mouseX, mouseY);
//                }
                    dragging.onDraw(mouseX, mouseY);

            }
        });

//        HUDMod hudMod = (HUDMod) TIMC.INSTANCE.getModuleCollection().get(HUDMod.class);
//
//        Pair<Color, Color> colors = HUDMod.getClientColors();

        boolean hovering = Utils.isHovering(width / 2f - 50, 20, 100, 20, mouseX, mouseY);

        resetButtonHover.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);

        float alpha = (float) (.5f + (.5 * resetButtonHover.getOutput().floatValue()));
//        Color color = ColorUtil.interpolateColorsBackAndForth(15, 1, colors.getFirst(), colors.getSecond(), false);
//        RoundedUtil.drawRoundOutline(width / 2f - 50, 20, 100, 20, 10, 2,
//                new Color(40, 40, 40, (int) (255 * alpha)), color);

//        FontUtil.tenacityBoldFont20.drawCenteredString("Reset Draggables", width / 2f, 20 + FontUtil.tenacityBoldFont20.getMiddleOfBox(20), -1);


        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

            if (this.handleComponentClick(ichatcomponent)) {
                return;
            }
        }

        this.inputField.mouseClicked(mouseX, mouseY, mouseButton);



        boolean hoveringResetButton = Utils.isHovering(width / 2f - 100, 20, 200, 20, mouseX, mouseY);
        if (hoveringResetButton && mouseButton == 0) {
            for (Dragging dragging : DragManager.draggables.values()) {
                dragging.setX(dragging.initialXVal);
                dragging.setY(dragging.initialYVal);
            }
            return;
        }


        DragManager.draggables.values().forEach(dragging -> {
            if (dragging.getModule().isEnabled()) {
//                if (dragging.getModule().equals(arraylistMod)) {
//                    dragging.onClickArraylist(arraylistMod, mouseX, mouseY, mouseButton);
//                } else {
//                    dragging.onClick(mouseX, mouseY, mouseButton);
//                }
                  dragging.onClick(mouseX, mouseY, mouseButton);
            }
        });


        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        DragManager.draggables.values().forEach(dragging -> {
            if (dragging.getModule().isEnabled()) {
                dragging.onRelease(state);
            }
        });

    }

    /**
     * Sets the text of the chat
     */
    protected void setText(String newChatText, boolean shouldOverwrite) {
        if (shouldOverwrite) {
            this.inputField.setText(newChatText);
        } else {
            this.inputField.writeText(newChatText);
        }
    }

    public void autocompletePlayerNames() {
        if (this.playerNamesFound) {
            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());

            if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
                this.autocompleteIndex = 0;
            }
        } else {
            int i = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
            this.foundPlayerNames.clear();
            this.autocompleteIndex = 0;
            String s = this.inputField.getText().substring(i).toLowerCase();
            String s1 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
            this.sendAutocompleteRequest(s1, s);

            if (this.foundPlayerNames.isEmpty()) {
                return;
            }

            this.playerNamesFound = true;
            this.inputField.deleteFromCursor(i - this.inputField.getCursorPosition());
        }

        if (this.foundPlayerNames.size() > 1) {
            StringBuilder stringbuilder = new StringBuilder();

            for (String s2 : this.foundPlayerNames) {
                if (stringbuilder.length() > 0) {
                    stringbuilder.append(", ");
                }

                stringbuilder.append(s2);
            }

            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(stringbuilder.toString()), 1);
        }

        this.inputField.writeText((String) this.foundPlayerNames.get(this.autocompleteIndex++));
    }

    private void sendAutocompleteRequest(String p_146405_1_, String p_146405_2_) {
        if (p_146405_1_.length() >= 1) {
            BlockPos blockpos = null;

            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                blockpos = this.mc.objectMouseOver.getBlockPos();
            }

            this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(p_146405_1_, blockpos));
            this.waitingOnAutocomplete = true;
        }
    }

    /**
     * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
     * message from the current cursor position
     */
    public void getSentHistory(int msgPos) {
        int i = this.sentHistoryCursor + msgPos;
        int j = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        i = MathHelper.clamp_int(i, 0, j);

        if (i != this.sentHistoryCursor) {
            if (i == j) {
                this.sentHistoryCursor = j;
                this.inputField.setText(this.historyBuffer);
            } else {
                if (this.sentHistoryCursor == j) {
                    this.historyBuffer = this.inputField.getText();
                }

                this.inputField.setText((String) this.mc.ingameGUI.getChatGUI().getSentMessages().get(i));
                this.sentHistoryCursor = i;
            }
        }
    }

    public void onAutocompleteResponse(String[] p_146406_1_) {
        if (this.waitingOnAutocomplete) {
            this.playerNamesFound = false;
            this.foundPlayerNames.clear();

            for (String s : p_146406_1_) {
                if (s.length() > 0) {
                    this.foundPlayerNames.add(s);
                }
            }

            String s1 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
            String s2 = StringUtils.getCommonPrefix(p_146406_1_);

            if (s2.length() > 0 && !s1.equalsIgnoreCase(s2)) {
                this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
                this.inputField.writeText(s2);
            } else if (this.foundPlayerNames.size() > 0) {
                this.playerNamesFound = true;
                this.autocompletePlayerNames();
            }
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame() {
        return false;
    }
}
