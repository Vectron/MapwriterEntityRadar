package Vectron.MWEntityRadar.Proxy;

import net.minecraftforge.common.MinecraftForge;
import Vectron.MWEntityRadar.Events.MWEREventHandlerServer;
import Vectron.MWEntityRadar.Network.PlayerLocToClient;
import Vectron.MWEntityRadar.utils.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class ProxyServer {
	
	public void preInit()
	{

	}
	
	public void init()
	{	
	}
	
	public void postInit()
	{	
		MWEREventHandlerServer handler = new MWEREventHandlerServer();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
	}
}
