package quantee.pgmstats;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class getLifetimeStats extends CommandBase{
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	
	@Override
	public String getCommandName() {
		return "lifetimestats";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Displays Lifetime Stats";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		try {
			
			if (PGMEventHandler.isLoaded) {
				Integer lifetimeKills = PGMEventHandler.lifetimeKills;
				Integer lifetimeDeaths = PGMEventHandler.lifetimeDeaths;
				
				Integer lifetimeObjDamage = PGMEventHandler.lifetimeObjDamage;
				Integer lifetimeMonuDestroys = PGMEventHandler.lifetimeMonuDestroys;
				Integer lifetimeWoolCaps = PGMEventHandler.lifetimeWoolCaps;
				Integer lifetimeSSs = PGMEventHandler.lifetimeSSs;
				Integer tempdeaths = lifetimeDeaths;
				
				if(tempdeaths == 0) {tempdeaths++;}
				
				double lifetimekpd = (double) (lifetimeKills / tempdeaths);
				
				sender.addChatMessage(new ChatComponentText("\n" + EnumChatFormatting.GREEN  + " ----------- [Lifetime Stats] -----------"));
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Lifetime kills: "  + EnumChatFormatting.BLUE + lifetimeKills));
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED    + "Lifetime deaths: " + EnumChatFormatting.BLUE + lifetimeDeaths));
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Lifetime K/D: "    + EnumChatFormatting.BLUE + lifetimekpd + "\n"));
				
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Lifetime objectives damaged: "  + EnumChatFormatting.BLUE + lifetimeObjDamage));
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Lifetime monuments destroyed: " + EnumChatFormatting.BLUE + lifetimeMonuDestroys));
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Lifetime wools captured: "      + EnumChatFormatting.BLUE + lifetimeWoolCaps));
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN  + "Lifetime SS gained: "           + EnumChatFormatting.BLUE + lifetimeSSs));
			}
			else {
				sender.addChatMessage(new ChatComponentText("\n" + EnumChatFormatting.GREEN  + " ----------- [Lifetime Stats] -----------"));
				sender.addChatMessage(new ChatComponentText("\n" + EnumChatFormatting.BLUE   + "[OCCT] " + EnumChatFormatting.RED + "Join a match to load your stats."));
			}
			
		}
		catch (Exception e) {
			sender.addChatMessage(new ChatComponentText("\n" + EnumChatFormatting.BLUE + "[OCCT] " + EnumChatFormatting.RED + "There was an error. Try restarting?"));
		}
		
	}
}
