package quantee.pgmstats;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class PGMTracker{
	
	private PGMEventHandler pgmeventhandler = new PGMEventHandler();
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ClientCommandHandler.instance.registerCommand(new getSessionStats());
		ClientCommandHandler.instance.registerCommand(new getLifetimeStats());
		ClientCommandHandler.instance.registerCommand(new OCCLevelingToggleCommand());
		ClientCommandHandler.instance.registerCommand(new GetLevel());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(pgmeventhandler);
		MinecraftForge.EVENT_BUS.register(this);		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
