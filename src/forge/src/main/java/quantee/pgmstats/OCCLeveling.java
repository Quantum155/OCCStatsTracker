package quantee.pgmstats;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

// This class handles the OCC leveling feature
public class OCCLeveling {	
	
	final static EnumChatFormatting bl = EnumChatFormatting.BLUE;
	final static EnumChatFormatting db = EnumChatFormatting.DARK_BLUE;
	final static EnumChatFormatting rd = EnumChatFormatting.RED;
	final static EnumChatFormatting gr = EnumChatFormatting.GREEN;
	final static EnumChatFormatting bo = EnumChatFormatting.BOLD;
	final static EnumChatFormatting ye = EnumChatFormatting.YELLOW;
	final static EnumChatFormatting re = EnumChatFormatting.RESET;
	final static EnumChatFormatting pu = EnumChatFormatting.LIGHT_PURPLE;
	final static EnumChatFormatting dp = EnumChatFormatting.DARK_PURPLE;
	final static EnumChatFormatting dr = EnumChatFormatting.DARK_RED;
	final static EnumChatFormatting dg = EnumChatFormatting.DARK_GREEN;
	final static EnumChatFormatting ga = EnumChatFormatting.GRAY;
	final static EnumChatFormatting da = EnumChatFormatting.DARK_AQUA;
	final static EnumChatFormatting aq = EnumChatFormatting.AQUA;
	final static EnumChatFormatting go = EnumChatFormatting.GOLD;
	
	final static String occt = bo + "" + gr + "[" + bl + "OC" + db + "CT" + gr + "] " + re;
	
	static boolean isLevelingLoaded = false;
	
	static Integer isLevelingEnabled = 0;  // Would be a boolean but saving only supports Integer. 0 -> false, 1 -> true. 
	static Integer xp = 0;
	static Integer level = 0;
	
	static final double exponent = 1.07;
	static final double baseXP = 2;
	
	// load line positions: isLevelingEnabled: 6, xp: 7
	
	public static void loadXp (int Xp) {
		if (!isLevelingLoaded && isLevelingEnabled == 1) {
		xp = Xp;
		}
	}
	
	public static void loadLevel (int Level) {
		if (isLevelingEnabled == 1) {
			level = Level;
		}
	}
	
	public static void loadIfEnabled (int enabled) {
		isLevelingEnabled = enabled;
	}
	
	
	//should be only triggerred from ingame.
	public static void CreditXp(int Xp) {
		if (isLevelingEnabled == 1) {
			EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
			xp += Xp;
			player.addChatComponentMessage(new ChatComponentText(pu + "+" + Xp + "XP"));
			
			if (xp > getLevelRequirement()) {
				int remaining_xp = xp - getLevelRequirement();
				player.addChatComponentMessage(new ChatComponentText("DEBUG: Carryover xp - XP: " + xp + "; LVL REQ: " + getLevelRequirement() + " Remaining: " + remaining_xp));
				xp = 0;
				level += 1;
				player.addChatComponentMessage(new ChatComponentText(bo + "" + gr + "Congratulations! You levelled up!"));
				player.addChatComponentMessage(new ChatComponentText(renderLevelString(level-1) + bl + " --> " + renderLevelString(level)));
				if (remaining_xp > 0) {
					
					CreditXp(remaining_xp);
				}
			}	
		}
	}
	
	public static int getLevelRequirement() {
		double expon_level = Math.pow(level, exponent);
		int xp_needed = OCCEventHandler.round(baseXP * expon_level);
		return xp_needed;
	}
	
	public static String renderLevelString(Integer Level) {
		String level = Level.toString();
		String[] characters = level.split("(?!^)");
		String output = "";
		
		if (Level < 30) {
			output += ga;
			output += "[" + level + "]";
		}
		else if (Level < 40) {
			output += da;
			output += "[" + level + "]";
		} 
		else if (Level < 50){
			output += gr;
			output += "[" + level + "]";
		}
		else if (Level < 60) {
			output += aq;
			output += "[" + level + "]";
		}
		else if (Level < 70) {
			output += dp;
			output += "[" + level + "]";
		}
		else if (Level < 80) {
			output += db;
			output += "[" + level + "]";
		}
		else if (Level < 90) {
			output += rd;
			output +=  "[" + level + "]";
		}
		else if (Level < 100) {
			output += go;
			output += "[" + level + "]";
		}
		else if (Level < 150) {
			output += da;
			output += "[" + characters[0];
			output += aq + characters[1];
			output += bl + characters[2] + "\u2736]";
		}
		else if (Level < 200) {
			output += go;
			output += "[" + characters[0];
			output += rd + characters[1];
			output += dr + characters[2] + "\u2738]";
		}
		else if (Level < 250) {
			output += bo;
			output += aq;
			output += "[" + characters[0];
			output += gr + characters[1];
			output += dg + characters[2] + "\u269D]";
		}
		else if (Level < 300) {
			output += bo;
			output += bl;
			output += "[" + characters[0];
			output += pu + characters[1];
			output += dp + characters[2] + "\u2756]";
		}
		else if (Level >= 300) {
			output += bo;
			output += rd;
			output += "[" + characters[0];
			output += go + characters[1];
			output += dg + characters[2];
			output += db + "✹]";
		}
		
		
		return output;
	}

}


// return math.floor(baseXP * (level ^ exponent))
