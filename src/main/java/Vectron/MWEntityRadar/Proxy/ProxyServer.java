package Vectron.MWEntityRadar.Proxy;

import Vectron.MWEntityRadar.Events.MWEREventHandlerServer;
import net.minecraftforge.common.MinecraftForge;

public class ProxyServer
{

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
	}
}
