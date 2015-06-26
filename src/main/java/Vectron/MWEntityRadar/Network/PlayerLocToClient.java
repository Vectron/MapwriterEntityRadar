package Vectron.MWEntityRadar.Network;

import io.netty.buffer.ByteBuf;
import Vectron.MWEntityRadar.Radar.MultiPlayers;
import Vectron.MWEntityRadar.Radar.MultiplayerManager;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

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
			
			Player = new MultiPlayers(name, x,y,z,dimension,rotation, loggedout);
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
		if (!messageIsValid) return;
		
		ByteBufUtils.writeUTF8String(buf, Player.name);
	    buf.writeInt(Player.x);
	    buf.writeInt(Player.y);
	    buf.writeInt(Player.z);
	    buf.writeInt(Player.dimension);
	    buf.writeDouble(Player.rotation);
	    buf.writeBoolean(Player.LoggedOut);
	}

	public static  class PlayerLocToClientHandler implements IMessageHandler<PlayerLocToClient, IMessage>
	{
		@Override
		public IMessage onMessage(PlayerLocToClient message, MessageContext ctx) 
		{
			if (ctx.side == Side.CLIENT)
			{
				MultiplayerManager.getInstance().updatePlayer(message.getPlayer());
			}
			return null; // no response in this case
		}
		
		// or in 1.8:
		//@Override
		//public IMessage onMessage(final PlayerLocToClient message, MessageContext ctx) 
		//{
		//	IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj; // or Minecraft.getMinecraft() on the client
		//    mainThread.addScheduledTask(new Runnable()
		//    {
		//    	@Override
		//    	public void run()
		//    	{
		//    		MultiplayerManager.getInstance().updatePlayer(message.getPlayer());
		//        }
		//    });
		//    return null; // no response in this case
		//}
	}
}
