// I write shit code. But it works. Thats enough for me. -Quantee
package quantee.pgmstats;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class OCCEventHandler {
	
	public static int round(double d){
	    double dAbs = Math.abs(d);
	    int i = (int) dAbs;
	    double result = dAbs - (double) i;
	    if(result<0.5){
	        return d<0 ? -i : i;            
	    }else{
	        return d<0 ? -(i+1) : i+1;          
	    }
	}
	
	//TODO need to refactor this code, move stats tracking out of event handler
	
	private static ArrayList<String> deathMessages = new ArrayList<String>();
	
	// Tracking all these variables here is a bad idea. Refactoring is on the TODO list.
	
	static Integer points = 0;  // Keeps track of ranked points - match specific
	static Integer deathCount = 0; // Keeps track of deaths - match specific
	static Integer killCount = 0; // Keeps track of kills - match specific
	static Integer killstreak = 0; // Keeps track of the active killstreak
	
	static Integer teamSwitchCount = 0; // Amounts the player has joined a team. >2 indicates team switching, (or switching to OBS). This should devaluate any stats.
	static Boolean isValid = true; // Used end match, false means the match was invalid. No longer used TODO need refactoring
	
	static Integer matchTickCount = 0; // Voting + pre match time > 1 minutes. TODO Do not count that time.
	static Integer matchScaling = 0;  // Increases every 5 minutes, used to give higher rewards for longer games TODO handle time measurement. Not a priority.
	
	static Integer maxpoints = 0; // Maximum points reachable (based on kills + teamwin etc - not real max) - used to calaculate rating
	
	//session tracker variables
	static public Integer sessionKills = 0;
	static public Integer sessionDeaths = 0;
	
	static public Integer sessionObjDamage = 0;
	static public Integer sessionMonuDestroys = 0;
	static public Integer sessionWoolCaps = 0;
	static public Integer sessionSSs = 0;
	
	//lifetime tracker variables, also bad code in terms of loading/writing. 
	static public Integer lifetimeKills = 0;
	static public Integer lifetimeDeaths = 0;
	
	static public Integer lifetimeObjDamage = 0;
	static public Integer lifetimeMonuDestroys = 0;
	static public Integer lifetimeWoolCaps = 0;
	static public Integer lifetimeSSs = 0;
	
	static boolean isLoaded = false; // Used to determine if the stats are loaded or not

	
	// final vars
	final static Integer killstreakBonusMultiplier = 2;
	// colors. Probably there is a better way, but refer to line 1.
	final static EnumChatFormatting bl = EnumChatFormatting.BLUE;
	final static EnumChatFormatting db = EnumChatFormatting.DARK_BLUE;
	final static EnumChatFormatting rd = EnumChatFormatting.RED;
	final static EnumChatFormatting gr = EnumChatFormatting.GREEN;
	final static EnumChatFormatting bo = EnumChatFormatting.BOLD;
	final static EnumChatFormatting ye = EnumChatFormatting.YELLOW;
	final static EnumChatFormatting re = EnumChatFormatting.RESET;
	final static EnumChatFormatting pu = EnumChatFormatting.LIGHT_PURPLE;
	
	final static String occt = bo + "" + gr + "[" + bl + "OC" + db + "CT" + gr + "] " + re;


	
	@SubscribeEvent
	public void onChatReceived(ClientChatReceivedEvent e)
	{		
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		
		String msg = e.message.getUnformattedText();
		ArrayList<String> formattedDeathMessages = new ArrayList<String>();
		
		deathMessages.add("PLAYER was slain by");
		deathMessages.add("PLAYER was shot by");
		deathMessages.add("PLAYER was shot off a high place by");
		deathMessages.add("PLAYER was shot out of the world by");
		deathMessages.add("PLAYER was sniped out of the world by");
		deathMessages.add("PLAYER was sniped off a high place by");
		deathMessages.add("PLAYER was knocked out of the world by");
		deathMessages.add("PLAYER was knocked off a high place by");
		deathMessages.add("PLAYER fell out of the world");
		deathMessages.add("PLAYER was punched out of the world by");
		deathMessages.add("PLAYER was punched off a high place by");
		deathMessages.add("PLAYER felt the fury of");
		deathMessages.add("PLAYER went splat from");
		deathMessages.add("PLAYER tripped and fell");
		
		for(String m : deathMessages)
		{
			formattedDeathMessages.add(m.replace("PLAYER", Minecraft.getMinecraft().thePlayer.getName()));
		}
		
		// Player joins a team
		if (msg.startsWith("You joined")) 
		{
						
		}
		
		// Player died.
		for(String m : formattedDeathMessages)
		{
			if (msg.startsWith(m)) 
			{
				if (isValid)
				{
					maxpoints += 3;
					if (killstreak > 2)
					{
						player.addChatComponentMessage(new ChatComponentText(occt + "" +  rd + "Shutdown! Lost killstreak of " + killstreak));
					}
					killstreak = 0;
					deathCount ++;
					sessionDeaths ++;
					lifetimeDeaths ++;
				}
			}

		}
		
		// Player gets a kill (OCC || Warzone)
		if ( (msg.startsWith("+") && (msg.endsWith("Kill") || msg.toLowerCase().endsWith("(wool holder)"))  && msg.contains("raindrops")) || msg.startsWith("+") && msg.endsWith("(Kill)"))
		{
			if (isValid)
			{
				maxpoints += 3;
				points += 3;
				killstreak ++;
				killCount ++;
				sessionKills ++;
				lifetimeKills ++;
				if (killstreak > 2)
				{
					Integer killStreakBonus = ((killstreak - 3) * killstreakBonusMultiplier) + 2; // Give bonus points for high killstreaks
					points += killStreakBonus;
					OCCLeveling.CreditXp(killStreakBonus);
					player.addChatComponentMessage(new ChatComponentText(occt + bl + "Killstreak! (" + killstreak + ")"));
				}
				OCCLeveling.CreditXp(5);
			}
		}
		
		if (isValid)
		{
			//player picks up wool
			if (msg.startsWith("You picked up") && msg.toLowerCase().contains("wool"))
			{
				points += 7;
				OCCLeveling.CreditXp(15);
			}
			
			//player caps wool
			if (msg.startsWith("+") && msg.endsWith("Wool Captured") && msg.contains("raindrops"))
			{
				points += 15;
				sessionWoolCaps ++;
				lifetimeWoolCaps ++;
				OCCLeveling.CreditXp(35);
			}
			
			//player damages objective
			if (msg.startsWith("You damaged"))
			{
				points += 6;
				sessionObjDamage ++;
				lifetimeObjDamage ++;
				OCCLeveling.CreditXp(15);
			}
			
			//player destroys monument
			if (msg.startsWith("+") && msg.endsWith("Monument Destroyed"))
			{
				points += 10;
				sessionMonuDestroys ++;
				lifetimeMonuDestroys ++;
				OCCLeveling.CreditXp(20);
			}
		}		
		
		
		// Match ends TODO FFA
		if (msg.startsWith("Your team")) // For team based
		{
			maxpoints += 15;
			killstreak = 0;
			teamSwitchCount = 0;
			
			matchTickCount = 0;  // see desc at first declaration
			matchScaling = 0; 
			
			if (isValid) 
			{
				if (msg.endsWith("lost")) // Player lost
				{
					maxpoints += 15;
					OCCLeveling.CreditXp(15);
				}
				else if (msg.endsWith("won!")) // Player won
				{
					points += 15;
					maxpoints += 6;
					OCCLeveling.CreditXp(50);
				}
				
				double precentage = points * 100 / maxpoints;
				String rating;
				
				// probably badcode but idontcare
				if (precentage >= 100) 
				{
					rating = "SS";
				}
				else if (precentage >= 97)
				{
					rating = "S";
				}
				else if (precentage >= 85)
				{
					rating = "A";
				}
				else if (precentage >= 70)
				{
					rating = "B";
				}
				else if (precentage >= 60)
				{
					rating = "C";
				}
				else if (precentage >= 40)
				{
					rating = "D";
				}
				else if (precentage >= 20)
				{
					rating = "E";
				}
				else if (precentage < 20)
				{
					rating = "F";
				}
				else
				{
					rating = "unrated";
				}
				if (rating == "SS")
				{
					sessionSSs ++;
					lifetimeSSs ++;
				}
				
				//Print match end stats
				player.addChatComponentMessage(new ChatComponentText(occt + gr + "Match performance rating: " + bl + rating + gr + " (" + bl + precentage + gr +"%)"));
				
				//saving stats
				
				File f = new File("OCCStatsSaveFile.txt");
				if(!f.exists()) { 
					player.addChatComponentMessage(new ChatComponentText(occt + rd + "OCC stats file not found. Regenerating..."));
					isLoaded = false;
				}
				
				try {
					StatsIO.saveStats();
					player.addChatMessage(new ChatComponentText(occt + gr + "Saved your stats."));
				} catch (IOException e1) {
					player.addChatMessage(new ChatComponentText(occt + rd + "There was an I/O error while saving your stats."));
				}
				
				
			}
			
			
			isValid = true;
			points = 0;
			maxpoints = 0;
		}
		
		deathMessages.clear();
	}
	
	@SubscribeEvent
	public void onClientDisconnectionFromServer(ClientDisconnectionFromServerEvent event) {
		if (isLoaded) {
			try {
				StatsIO.saveStats();
				System.out.println("Stats saved");
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		isLoaded = false;
		OCCLeveling.isLevelingLoaded = false;
	}
	
	@SubscribeEvent
	public void onClientConnectedToServer(ClientConnectedToServerEvent event) {
		isLoaded = true;
		
		File f = new File("OCCStatsSaveFile.txt");
		if(!f.exists() && !f.isDirectory()) { 
			isLoaded = false;
			try {
				StatsIO.saveStats();
			} catch (IOException e1) {
			}
		}
		
		try{
		    ArrayList<Integer> loadlist = StatsIO.loadStats();
		    lifetimeKills = loadlist.get(0);
		    lifetimeDeaths = loadlist.get(1);
		    lifetimeObjDamage = loadlist.get(2);
		    lifetimeMonuDestroys = loadlist.get(3);
		    lifetimeWoolCaps = loadlist.get(4);
		    lifetimeSSs = loadlist.get(5);
		    // Pass OCCLeveling the data. If it is already loaded it will discard it.
		    OCCLeveling.loadIfEnabled(loadlist.get(6));
		    OCCLeveling.loadXp(loadlist.get(7));
		    OCCLeveling.loadLevel(loadlist.get(8));
		    
		    isLoaded = true;
		    OCCLeveling.isLevelingLoaded = true;
		    // player.addChatMessage(new ChatComponentText("\n" + occt + gr + "Loaded your stats!"));
		    
		    
		}catch (Exception e1) {
			System.out.println("[OCCT] IO load error occurred. Stack trace:");
			System.out.println(e1);
			isLoaded = false;
		}
	}
}
