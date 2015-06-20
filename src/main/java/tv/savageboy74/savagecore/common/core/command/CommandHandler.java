package tv.savageboy74.savagecore.common.core.command;

/*
 * CommandHandler.java
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

import net.minecraft.command.CommandException;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import tv.savageboy74.savagecore.common.core.helper.StringHelper;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class CommandHandler extends CommandBase
{
    public static CommandHandler instance = new CommandHandler();

    private static TMap<String, ISubCommand> commands = new THashMap<String, ISubCommand>();

    static
    {
        registerSubCommand(CommandHelp.instance);
        registerSubCommand(CommandVersion.instance);
    }

    public static void initCommands(FMLServerStartingEvent event)
    {
        event.registerServerCommand(instance);
    }

    public static boolean registerSubCommand(ISubCommand command)
    {
        if(!commands.containsKey(command.getCommandName())) {
            commands.put(command.getCommandName(), command);
            return true;
        }

        return false;
    }

    @Override
    public String getName() {
        return "savagecore";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/" + getName() + " help";
    }

    @Override
    public void execute(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length <= 0) {
            throw new CommandException("Type '" + getCommandUsage(sender) + "' for help.");
        }

        if (commands.containsKey(args[0])) {
            commands.get(args[0]).handleCommand(sender, args);
            return;
        }

        throw new CommandException("Type '" + getCommandUsage(sender) + "' for help.");
    }

    @Override
    public List getAliases()
    {
        List altName = new ArrayList();

        altName.add("sc");

        return altName;
    }
}