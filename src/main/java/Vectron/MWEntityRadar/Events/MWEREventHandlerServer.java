package Vectron.MWEntityRadar.Events;

import Vectron.MWEntityRadar.Network.PlayerLocToClient;
import Vectron.MWEntityRadar.Radar.MultiPlayers;
import Vectron.MWEntityRadar.utils.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class MWEREventHandlerServer
{
	@SubscribeEvent
	public void UpdateEntityEvent(LivingEvent.LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
			int x = (int) player.posX;
			int y = (int) player.posY;
			int z = (int) player.posZ;
			int dim = player.dimension;
			double rot = player.rotationYaw + 180;
			String name = player.getDisplayNameString();

			MultiPlayers mplayer = new MultiPlayers(x, y, z, dim, name, rot, false);
			Reference.simpleNetworkWrapper.sendToAll(new PlayerLocToClient(mplayer));
		}
	}

	@SubscribeEvent
	public void PlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event)
	{
		String name = event.player.getDisplayNameString();
		MultiPlayers mplayer = new MultiPlayers(-1, -1, -1, -1, name, 0, true);
		Reference.simpleNetworkWrapper.sendToAll(new PlayerLocToClient(mplayer));
	}
}
