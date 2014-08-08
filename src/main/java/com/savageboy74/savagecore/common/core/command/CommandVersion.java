package com.savageboy74.savagecore.common.core.command;

import com.savageboy74.savagecore.common.core.helper.Font;
import com.savageboy74.savagecore.common.core.helper.StringHelper;
import com.savageboy74.savagecore.common.util.Reference;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class CommandVersion implements ISubCommand
{
    public static CommandVersion instance = new CommandVersion();

    @Override
    public String getCommandName()
    {
        return "version";
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] arguments)
    {
        sender.addChatMessage (new ChatComponentText(Font.Color.AQUA + "[SavageCore] " + Font.Format.RESET +  StringHelper.localize("info.savagecore.command.version.0") + " " + EnumChatFormatting.LIGHT_PURPLE + Reference.VERSION + "."));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        return null;
    }
}
