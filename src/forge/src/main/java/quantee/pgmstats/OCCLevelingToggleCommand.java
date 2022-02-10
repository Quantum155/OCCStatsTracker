package quantee.pgmstats;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class OCCLevelingToggleCommand extends CommandBase {
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	
	@Override
	public String getCommandName() {
		return "togglexpleveling";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Toggles OCCStatsTracker's XP leveling feature.";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Toggled XP leveling!"));
		if (OCCLeveling.isLevelingEnabled == 0) {OCCLeveling.isLevelingEnabled = 1;}
		else {OCCLeveling.isLevelingEnabled = 0;}
	}
}
