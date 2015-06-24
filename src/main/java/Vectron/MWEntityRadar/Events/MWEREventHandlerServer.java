package Vectron.MWEntityRadar.Events;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import Vectron.MWEntityRadar.Network.PlayerLocToClient;
import Vectron.MWEntityRadar.Radar.MultiPlayers;
import Vectron.MWEntityRadar.utils.Reference;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class MWEREventHandlerServer 
{
	@SubscribeEvent
    public void UpdateEntityEvent(LivingEvent.LivingUpdateEvent event)
	{
		if (event.entity instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) event.entity;
			int x = (int) player.posX;
			int y = (int) player.posY;
			int z = (int) player.posZ;
			int dim = player.dimension;
			double rot = player.rotationYaw + 180;
			String name = player.getDisplayName();
			
			MultiPlayers mplayer = new MultiPlayers(x, y, z, dim, name, rot, false);
			Reference.simpleNetworkWrapper.sendToAll(new PlayerLocToClient(mplayer));
		}
    }
	
	@SubscribeEvent
	public void PlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event)
	{
		String name = event.player.getDisplayName();
		MultiPlayers mplayer = new MultiPlayers(-1, -1, -1, -1, name, 0, true);
		Reference.simpleNetworkWrapper.sendToAll(new PlayerLocToClient(mplayer));	
	}
}
