package Vectron.MWEntityRadar;

import java.util.logging.Logger;

import Vectron.MWEntityRadar.Network.PlayerLocToClient;
import Vectron.MWEntityRadar.Proxy.ProxyServer;
import Vectron.MWEntityRadar.utils.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION,
		dependencies = Reference.DEPENDENCIES, acceptableRemoteVersions = "*",
		acceptedMinecraftVersions = "@ACCEPTED_MC_VERSION@")
public class MWEntityRadar
{
	// The instance of your mod that Forge uses.
	@Instance(Reference.MOD_ID)
	public static MWEntityRadar instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,
			serverSide = Reference.SERVER_PROXY_CLASS)
	public static ProxyServer proxy;
	public static Logger log = Logger.getLogger(Reference.MOD_ID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Reference.simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(
				Reference.MOD_ID);
		Reference.simpleNetworkWrapper.registerMessage(
				PlayerLocToClient.PlayerLocToClientHandler.class,
				PlayerLocToClient.class,
				Reference.PLAYER_LOCATION_MESSAGE_ID,
				Side.SERVER);
		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}
}
