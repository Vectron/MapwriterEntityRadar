package Vectron.MWEntityRadar.Proxy;

import Vectron.MWEntityRadar.Events.MWEREventHandler;
import Vectron.MWEntityRadar.Network.PlayerLocToClient;
import Vectron.MWEntityRadar.Overlay.PlayerOverlay;
import Vectron.MWEntityRadar.utils.Reference;
import mapwriter.api.MwAPI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;

public class ProxyClient extends ProxyServer
{

	public static Minecraft mc = null;

	@Override
	public void preInit()
	{
		super.preInit();
		Reference.simpleNetworkWrapper.registerMessage(
				PlayerLocToClient.PlayerLocToClientHandler.class,
				PlayerLocToClient.class,
				Reference.PLAYER_LOCATION_MESSAGE_ID,
				Side.CLIENT);
	}

	@Override
	public void init()
	{
		super.init();
	}

	@Override
	public void postInit()
	{
		super.postInit();
		mc = Minecraft.getMinecraft();
		MinecraftForge.EVENT_BUS.register(new MWEREventHandler());
		MwAPI.registerDataProvider(Reference.mwDataProviderName, PlayerOverlay.instance());
		MwAPI.setCurrentDataProvider(Reference.mwDataProviderName);
	}
}
