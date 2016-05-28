package Vectron.MWEntityRadar.Radar;

import java.awt.Point;

import org.lwjgl.opengl.GL11;

import Vectron.MWEntityRadar.Proxy.ProxyClient;
import Vectron.MWEntityRadar.utils.Utils;
import mapwriter.api.IMapMode;
import mapwriter.api.IMapView;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class MultiPlayers
{
	public final String name;
	public int x;
	public int y;
	public int z;
	public int dimension;
	public double rotation;
	public ResourceLocation skin;

	public boolean LoggedOut;

	public Point.Double screenPos = new Point.Double(0, 0);

	public MultiPlayers(int x, int y, int z, int dimension, String PlayerName, double Rotation,
			boolean loggedout)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = PlayerName;
		this.dimension = dimension;
		this.rotation = Rotation;
		this.LoggedOut = loggedout;
	}

	public void draw(IMapMode mapMode, IMapView mapView)
	{
		if (mapView.getDimension() == this.dimension)
		{
			if (this.skin == null)
			{
				this.skin = Utils.GetSkin(this.name);
			}

			double arrowSize = mapMode.getConfig().getPlayerArrowSize() * 1.0;
			double w = arrowSize;
			double h = arrowSize;

			double zDepth = 0.0;
			if (mapMode.getConfig().getCircular())
			{
				zDepth = -1.0;
			}

			double scale = mapView.getDimensionScaling(this.dimension);
			Point.Double p = mapMode.blockXZtoScreenXY(mapView, this.x * scale, this.z * scale);
			this.screenPos.setLocation(
					p.x + mapMode.getXTranslation(),
					p.y + mapMode.getYTranslation());

			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(255, 255, 255, 255);
			GlStateManager.disableBlend();

			// ProxyClient.mc.renderEngine.bindTexture(Reference.playerMPArrowTexture);
			ProxyClient.mc.renderEngine.bindTexture(skin);

			Utils.drawRotatingScaledCustomSizeModalRect(
					p.x,
					p.y,
					8,
					8,
					8,
					8,
					w,
					h,
					64.0F,
					64.0F,
					this.rotation,
					zDepth);
		}
	}
}
