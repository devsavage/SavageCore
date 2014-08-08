package com.savageboy74.savagecore.common.core.command;

import net.minecraft.command.ICommandSender;

import java.util.List;

public interface ISubCommand
{

    public String getCommandName();

    public void handleCommand(ICommandSender sender, String[] arguments);

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args);
}