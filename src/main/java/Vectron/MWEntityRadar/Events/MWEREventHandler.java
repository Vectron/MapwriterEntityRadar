package Vectron.MWEntityRadar.Events;

import Vectron.MWEntityRadar.Radar.MultiplayerManager;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MWEREventHandler
{

	@SubscribeEvent
	public void EntityJoinedTheWorld(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntityOtherPlayerMP)
		{
			// System.out.println(String.format("%s joined dimension %d,
			// isRemote:%b",event.entity.toString(), event.entity.dimension,
			// event.world.isRemote));
			MultiplayerManager.getInstance().addPlayer((EntityOtherPlayerMP) event.getEntity());
		}
	}
}
