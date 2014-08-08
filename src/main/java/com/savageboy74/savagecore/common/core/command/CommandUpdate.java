package com.savageboy74.savagecore.common.core.command;

import com.savageboy74.savagecore.common.core.helper.Font;
import com.savageboy74.savagecore.common.core.helper.StringHelper;
import com.savageboy74.savagecore.common.util.Reference;
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
