package Vectron.MWEntityRadar.utils;

import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class Reference 
{
	public static final String MOD_ID = "mwentityradar";
	public static final String MOD_NAME = "Map Writer Entity Radar";
	public static final String VERSION = "@MOD_VERSION@";
    public static final String DEPENDENCIES =  "after:MapWriter";
	public static final String MOD_GUIFACTORY_CLASS = "mapwriter.gui.ModGuiFactoryHandler";
	
	public static final String CLIENT_PROXY_CLASS = "Vectron.MWEntityRadar.Proxy.ProxyClient";
	public static final String SERVER_PROXY_CLASS = "Vectron.MWEntityRadar.Proxy.ProxyServer";
	
	public static final String catOptions = "options";
	
	public static final String mwDataProviderName = "EntityRadar";
	
	public static final ResourceLocation playerMPArrowTexture = new ResourceLocation(MOD_ID, "textures/radar/arrow_player_mp.png");
	
	public static SimpleNetworkWrapper simpleNetworkWrapper; // used to transmit your network messages
	public static final byte PLAYER_LOCATION_MESSAGE_ID = 1;
}
