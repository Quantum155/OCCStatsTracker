package quantee.pgmstats;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class getSessionStats extends CommandBase{
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	
	@Override
	public String getCommandName() {
		return "sessionstats";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Displays Session Stats";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		try {
		Integer sessionKills = PGMEventHandler.sessionKills;
		Integer sessionDeaths = PGMEventHandler.sessionDeaths;
		
		Integer sessionObjDamage = PGMEventHandler.sessionObjDamage;
		Integer sessionMonuDestroys = PGMEventHandler.sessionMonuDestroys;
		Integer sessionWoolCaps = PGMEventHandler.sessionWoolCaps;
		Integer sessionSSs = PGMEventHandler.sessionSSs;
		
		Integer tempdeaths = sessionDeaths;
		
		if(tempdeaths == 0) {tempdeaths++;}
		
		double sessionkpd = (double) (sessionKills / tempdeaths);
		
		sender.addChatMessage(new ChatComponentText("\n" + EnumChatFormatting.GREEN  + " ----------- [Session Stats] -----------"));
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Session kills: "  + EnumChatFormatting.BLUE + sessionKills));
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED    + "Session deaths: " + EnumChatFormatting.BLUE + sessionDeaths));
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Sessiok K/D: "    + EnumChatFormatting.BLUE + sessionkpd + "\n"));
		
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Session objectives damaged: "  + EnumChatFormatting.BLUE + sessionObjDamage));
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Session monuments destroyed: " + EnumChatFormatting.BLUE + sessionMonuDestroys));
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Session wools captured: "      + EnumChatFormatting.BLUE + sessionWoolCaps));
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Session SS gained: "           + EnumChatFormatting.BLUE + sessionSSs));
		}
		catch (Exception e) {
			sender.addChatMessage(new ChatComponentText("\n" + EnumChatFormatting.BLUE + "[OCCT] " + EnumChatFormatting.RED + "There was an error. Try restarting?"));
		}
		
	}
	
	
}
