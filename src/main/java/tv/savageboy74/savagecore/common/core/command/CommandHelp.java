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

import tv.savageboy74.savagecore.common.core.helper.StringHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class CommandHelp implements ISubCommand {

    public static CommandHelp instance = new CommandHelp();

    /* ISubCommand */
    @Override
    public String getCommandName() {

        return "help";
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] arguments) {

        if (arguments.length == 0) {
            return;
        }
        if (arguments.length == 1) {

            StringBuilder output = new StringBuilder(StringHelper.localize("info.savagecore.command.help.0") + " ");
            List<String> commandList = new ArrayList<String>(CommandHandler.getCommandList());

            for (int i = 0; i < commandList.size() - 1; i++) {
                output.append(StringHelper.YELLOW + "/savagecore " + commandList.get(i) + StringHelper.WHITE + ", ");
            }
            output.delete(output.length() - 2, output.length());
            output.append(" and /savagecore " + commandList.get(commandList.size() - 1) + ".");
            sender.addChatMessage(new ChatComponentText(output.toString()));
        } else {
            String commandName = arguments[1];
            sender.addChatMessage(new ChatComponentText(StringHelper.localize("info.savagecore.command." + commandName)));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {

        if (args.length == 2) {
            return CommandBase.getListOfStringsFromIterableMatchingLastWord(args, CommandHandler.getCommandList());
        }
        return null;

    }

}