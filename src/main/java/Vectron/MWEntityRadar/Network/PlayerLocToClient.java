package Vectron.MWEntityRadar.Network;

import Vectron.MWEntityRadar.Radar.MultiPlayers;
import Vectron.MWEntityRadar.Radar.MultiplayerManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerLocToClient implements IMessage
{
	private boolean messageIsValid;
	private MultiPlayers Player;

	public PlayerLocToClient(MultiPlayers player)
	{
		Player = player;
		messageIsValid = true;
	}

	public MultiPlayers getPlayer()
	{
		return Player;
	}

	public boolean isMessageValid()
	{
		return messageIsValid;
	}

	// for use by the message handler only.
	public PlayerLocToClient()
	{
		messageIsValid = false;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		try
		{
			String name = ByteBufUtils.readUTF8String(buf);
			int x = buf.readInt();
			int y = buf.readInt();
			int z = buf.readInt();
			int dimension = buf.readInt();
			double rotation = buf.readDouble();
			boolean loggedout = buf.readBoolean();

			Player = new MultiPlayers(x, y, z, dimension, name, rotation, loggedout);
		}
		catch (IndexOutOfBoundsException ioe)
		{
			System.err.println("Exception while reading TargetEffectMessageToClient: " + ioe);
		}

		messageIsValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		if (!messageIsValid)
			return;

		ByteBufUtils.writeUTF8String(buf, Player.name);
		buf.writeInt(Player.x);
		buf.writeInt(Player.y);
		buf.writeInt(Player.z);
		buf.writeInt(Player.dimension);
		buf.writeDouble(Player.rotation);
		buf.writeBoolean(Player.LoggedOut);
	}

	public static class PlayerLocToClientHandler implements
			IMessageHandler<PlayerLocToClient, IMessage>
	{
		/*
		 * @Override
		 * public IMessage onMessage(PlayerLocToClient message, MessageContext
		 * ctx)
		 * {
		 * if (ctx.side == Side.CLIENT)
		 * {
		 * MultiplayerManager.getInstance().updatePlayer(message.getPlayer());
		 * }
		 * return null; // no response in this case
		 * }
		 */

		// or in 1.8:
		@Override
		public IMessage onMessage(final PlayerLocToClient message, MessageContext ctx)
		{
			if (ctx.side == Side.CLIENT)
			{
				IThreadListener mainThread = Minecraft.getMinecraft(); // (WorldServer)
																		// ctx.getServerHandler().playerEntity.worldObj;
				mainThread.addScheduledTask(new Runnable()
				{
					@Override
					public void run()
					{
						MultiplayerManager.getInstance().updatePlayer(message.getPlayer());
					}
				});
			}
			return null; // no response in this case
		}
	}
}
