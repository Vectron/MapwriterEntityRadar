package Vectron.MWEntityRadar.Radar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Vectron.MWEntityRadar.Proxy.ProxyClient;
import mapwriter.api.IMapMode;
import mapwriter.api.IMapView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

public class MultiplayerManager
{

	public static MultiplayerManager instance = null;

	private Map<String, MultiPlayers> kvMultiplayers = new HashMap<String, MultiPlayers>();

	private boolean ServerUpdatesPlayers = false;

	protected MultiplayerManager()
	{
	}

	public static MultiplayerManager getInstance()
	{
		if (instance == null)
		{
			instance = new MultiplayerManager();
		}

		return instance;
	}

	public void updateMPPlayers()
	{
		if (ServerUpdatesPlayers)
		{
			return;
		}

		Iterator it = kvMultiplayers.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pair = (Map.Entry) it.next();
			EntityPlayer player = ProxyClient.mc.theWorld.getPlayerEntityByName(
					pair.getKey().toString());

			if (player != null)
			{
				MultiPlayers element = (MultiPlayers) pair.getValue();
				element.x = (int) Math.floor(player.posX);
				element.y = (int) Math.floor(player.posY);
				element.z = (int) Math.floor(player.posZ);
				element.dimension = player.dimension;
				element.rotation = player.rotationYaw + 180;
			}
			else
			{
				it.remove(); // avoids a ConcurrentModificationException
			}
		}
	}

	public void DrawArrows(IMapMode mapMode, IMapView mapView)
	{
		for (Map.Entry<String, MultiPlayers> entry : kvMultiplayers.entrySet())
		{
			entry.getValue().draw(mapMode, mapView);
		}
	}

	public MultiPlayers getPlayersNearScreenPos(int x, int y)
	{
		MultiPlayers nearMPPlayer = null;

		for (Map.Entry<String, MultiPlayers> entry : kvMultiplayers.entrySet())
		{
			MultiPlayers marker = entry.getValue();
			if (marker != null)
			{
				if (marker.screenPos.distanceSq(x, y) < 6.0)
				{
					nearMPPlayer = marker;
				}
			}
		}
		return nearMPPlayer;
	}

	public void addPlayer(EntityOtherPlayerMP playerMP)
	{
		String Name = playerMP.getDisplayNameString();
		int x = (int) Math.floor(playerMP.posX);
		int y = (int) Math.floor(playerMP.posY);
		int z = (int) Math.floor(playerMP.posZ);
		int dim = playerMP.dimension;
		double PlayerRotationDegrees = playerMP.rotationYaw + 180;

		MultiPlayers player = new MultiPlayers(x, y, z, dim, Name, PlayerRotationDegrees, false);
		kvMultiplayers.put(Name, player);
	}

	public void updatePlayer(MultiPlayers player)
	{
		this.ServerUpdatesPlayers = true;
		if (Minecraft.getMinecraft().thePlayer.getDisplayNameString().equals(player.name))
		{
			return;
		}

		if (player.LoggedOut)
		{
			kvMultiplayers.remove(player.name);
		}

		MultiPlayers element = kvMultiplayers.get(player.name);
		if (element != null)
		{
			element.x = player.x;
			element.y = player.y;
			element.z = player.z;
			element.dimension = player.dimension;
			element.rotation = player.rotation;
		}
		else
		{
			kvMultiplayers.put(player.name, player);
		}
	}

	public void removePlayer(MultiPlayers player)
	{
		kvMultiplayers.remove(player.name);
	}

	public void InitList()
	{
		// multiplayerList.clear();
		for (int i = 0; i < ProxyClient.mc.theWorld.playerEntities.size(); i++)
		{
			Object obj = ProxyClient.mc.theWorld.playerEntities.get(i);
			if (obj instanceof EntityOtherPlayerMP)
			{
				EntityOtherPlayerMP playerMP = ((EntityOtherPlayerMP) obj);

				String Name = playerMP.getDisplayNameString();
				int x = (int) Math.floor(playerMP.posX);
				int y = (int) Math.floor(playerMP.posY);
				int z = (int) Math.floor(playerMP.posZ);
				int dim = playerMP.dimension;
				double PlayerRotationDegrees = playerMP.rotationYaw + 180;

				MultiPlayers player = new MultiPlayers(
						x,
						y,
						z,
						dim,
						Name,
						PlayerRotationDegrees,
						false);
				// multiplayerList.add(player);
			}
		}
	}
}
