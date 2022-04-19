package quantee.pgmstats;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

// This class handles the OCC leveling feature
public class OCCLeveling {	

	// Very bad code. In the end this allows for very short color codes.
	final static EnumChatFormatting bl = EnumChatFormatting.BLUE;
	final static EnumChatFormatting db = EnumChatFormatting.DARK_BLUE;
	final static EnumChatFormatting rd = EnumChatFormatting.RED;
	final static EnumChatFormatting gr = EnumChatFormatting.GREEN;
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
	final static EnumChatFormatting wh = EnumChatFormatting.WHITE;
	final static EnumChatFormatting bold = EnumChatFormatting.BOLD;
	
	final static String occt = bold + "" + gr + "[" + bl + "OC" + db + "CT" + gr + "] " + re;
	
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
				player.addChatComponentMessage(new ChatComponentText(bold + "" + gr + "Congratulations! You levelled up!"));
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
		return Math.min(xp_needed, 1500);
	}

	public static String renderLevelString(Integer Level) {
		String level = Level.toString();
		String[] characters = level.split("(?!^)");
		String output = "";
		
		if (Level < 50) {
			output += ga;
			output += "[" + level + "]";
		}
		else if (Level < 100) {
			output += wh;
			output += "[" + level + "]";
		} 
		else if (Level < 150){
			output += da;
			output += "[" + level + "]";
		}
		else if (Level < 200) {
			output += aq;
			output += "[" + level + "]";
		}
		else if (Level < 250) {
			output += dg;
			output += "[" + level + "]";
		}
		else if (Level < 300) {
			output += gr;
			output += "[" + level + "]";
		}
		else if (Level < 350) {
			output += go;
			output +=  "[" + level + "]";
		}
		else if (Level < 400) {
			output += ye;
			output += "[" + level + "]";
		}
		else if (Level < 450) {
			output += bold;
			output += rd;
			output += "[" + level + "]";
		}
		else if (Level < 500) {
			output += bold;
			output += db;
			output += "[" + characters[0];
			output += da + characters[1];
			output += aq + characters[2] + "]";
		}
		else if (Level < 600) {
			output += bold;
			output += ye;
			output += "[" + characters[0];
			output += go + characters[1];
			output += ye + characters[2] + "]";
		}
		else if (Level < 700) {
			output += bold;
			output += dg;
			output += "[" + characters[0];
			output += da + characters[1];
			output += aq + characters[2] + "]";
		}
		else if (Level < 800) {
			output += bold;
			output += ye;
			output += "[\u272F" + characters[0];
			output += go + characters[1];
			output += dr + characters[2];
			output += "]";
		}
		else if (Level < 900) {
			output += bold;
			output += pu;
			output += "[\u272F" + bl + characters[0];
			output += aq + characters[1];
			output += db + characters[2];
			output += "]";
		}
		else if (Level < 1000) {
			output += bold;
			output += dg;
			output += "[\u272F" + ye + characters[0]; // \u269D
			output += go + characters[1];
			output += gr + characters[2];
			output += "]";
		}
		else if (Level < 1500) {
			output += bold;
			output += aq + "LEG" + da + "END" + wh + "ARY" + db + " \u2605 ";
			output += ye + characters[0] + characters[1];
			output += go + characters[2] + characters[3];
		}
		else if (Level < 2000) {
			output += bold;
			output += dr + "GOD" + wh + "LIKE" + rd + " \u2605 ";
			output += gr + characters[0] + characters[1];
			output += ye + characters[2] + characters[3];
		}
		else if (Level < 2500) {
			output += bold;
			output += ye + "CEL" + go + "EST" + wh + "IAL" + gr + " \u269D ";
			output += aq + characters[0] + characters[1];
			output += pu + characters[2] + characters[3];
		}
		else{
			output += bold;
			output += db + "ASC" + dg + "END" + dr + "ED" + wh + " \u269D ";
			output += dr + characters[0] + characters[1];
			output += rd + characters[2] + characters[3];
		}
		
		
		return output;
	}

}


// return math.floor(baseXP * (level ^ exponent))
