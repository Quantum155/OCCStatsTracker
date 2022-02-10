package quantee.pgmstats;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class GetLevel extends CommandBase {
	final static EnumChatFormatting gr = EnumChatFormatting.GREEN;
	final static EnumChatFormatting bl = EnumChatFormatting.BLUE;
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	
	@Override
	public String getCommandName() {
		return "getlevel";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Gets your local OCC level, if you have enabled that feature";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		sender.addChatMessage(new ChatComponentText("\n" + gr  + " ----------- [OCCT LEVELING] -----------"));
		sender.addChatMessage(new ChatComponentText(gr + "Your level: " + OCCLeveling.renderLevelString(OCCLeveling.level)));
		sender.addChatMessage(new ChatComponentText(gr + "Your XP: " + bl + OCCLeveling.xp + gr + " / " + bl + OCCLeveling.getLevelRequirement()));
		sender.addChatMessage(new ChatComponentText(gr + "XP Needed: " + bl + ( OCCLeveling.getLevelRequirement() - OCCLeveling.xp )));
	}
}
