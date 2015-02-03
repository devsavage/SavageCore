package tv.savageboy74.savagecore.common.core.command;

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