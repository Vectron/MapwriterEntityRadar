package Vectron.MWEntityRadar.Events;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import Vectron.MWEntityRadar.MWEntityRadar;
import Vectron.MWEntityRadar.Radar.MultiplayerManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.*;

public class MWEREventHandler {

    @SubscribeEvent
    public void EntityJoinedTheWorld(EntityJoinWorldEvent event){
		if (event.entity instanceof  EntityOtherPlayerMP)
		{
			MultiplayerManager.getInstance().addPlayer((EntityOtherPlayerMP)event.entity);
		}
    }
}
