package tv.savageboy74.savagecore.common.core.command;

/*
 * CommandUpdate.java
 * Copyright (C) 2014 - 2015 Savage - github.com/savageboy74
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import tv.savageboy74.savagecore.common.core.helper.Font;
import tv.savageboy74.savagecore.common.util.Reference;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.List;

public class CommandUpdate implements ISubCommand
{
    public static CommandUpdate instance = new CommandUpdate();



    @Override
    public String getCommandName()
    {
        return "update";
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] arguments)
    {

        final String messageUpdateURL = Font.Color.AQUA + "[SavageCore] " + Font.Format.RESET +  Font.Color.YELLOW + "Click " + Font.Color.AQUA + Font.Format.ITALIC + Font.Format.UNDERLINE + "here" + Font.Format.RESET + Font.Color.YELLOW + " to download a new update for " + "SavageCore. " + Font.Format.RESET +  "Current SavageCore Version: " + EnumChatFormatting.DARK_GREEN + Reference.VERSION + ".";

        final IChatComponent chatComponentUpdate = new ChatComponentText(messageUpdateURL);
        chatComponentUpdate.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://savageboy74.tv/mods/savagecore/"));
        chatComponentUpdate.getChatStyle().setUnderlined(false);
        sender.addChatMessage(chatComponentUpdate);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        return null;
    }
}
