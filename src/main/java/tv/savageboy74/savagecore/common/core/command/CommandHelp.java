package tv.savageboy74.savagecore.common.core.command;

/*
 * CommandHelp.java
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

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import scala.tools.nsc.backend.icode.ReferenceEquality;
import tv.savageboy74.savagecore.common.core.helper.StringHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import tv.savageboy74.savagecore.common.util.SavageCoreProps;

import java.util.ArrayList;
import java.util.List;

public class CommandHelp implements ISubCommand
{
    public static CommandHelp instance = new CommandHelp();

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] arguments)
    {
        sender.addChatMessage(new ChatComponentText("================"));
        sender.addChatMessage(new ChatComponentText("|" + EnumChatFormatting.AQUA + " SavageCore"  + EnumChatFormatting.GOLD + " Help "+ EnumChatFormatting.RESET + "|"));
        sender.addChatMessage(new ChatComponentText("================"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "/" + SavageCoreProps.MOD_ID + EnumChatFormatting.RED + " " + CommandVersion.instance.getCommandName() + ": " + EnumChatFormatting.RESET + "Returns the current version of " + SavageCoreProps.MOD_NAME + " you are using."));

        if(Loader.isModLoaded("savagetech"))
        {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "/savagecore " + EnumChatFormatting.RED + "stversion" + ": " + EnumChatFormatting.RESET + "Returns the current version of SavageTech you are using and update info."));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}