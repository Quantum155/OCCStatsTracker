package quantee.pgmstats;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class debugAddXp extends CommandBase {
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	
	@Override
	public String getCommandName() {
		return "deebugaddxp";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Debug add xp";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		int xp = Integer.parseInt(args[0]);  
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE  + " Adding " + EnumChatFormatting.BLUE + xp + EnumChatFormatting.LIGHT_PURPLE + " XP"));
		OCCLeveling.CreditXp(xp);
		
	}
}
