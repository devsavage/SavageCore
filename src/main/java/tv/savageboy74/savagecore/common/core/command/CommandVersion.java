package tv.savageboy74.savagecore.common.core.command;

/*
 * CommandVersion.java
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
import tv.savageboy74.savagecore.common.core.helper.StringHelper;
import tv.savageboy74.savagecore.common.util.SavageCoreProps;
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
        sender.addChatMessage (new ChatComponentText(Font.Color.AQUA + "[SavageCore] " + Font.Format.RESET +  StringHelper.localize("info.savagecore.command.version.0") + " " + EnumChatFormatting.LIGHT_PURPLE + SavageCoreProps.VERSION));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        return null;
    }
}
