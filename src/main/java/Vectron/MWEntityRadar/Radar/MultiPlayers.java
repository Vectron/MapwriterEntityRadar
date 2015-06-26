package Vectron.MWEntityRadar.Radar;

import java.awt.Point;

import mapwriter.MwUtil;
import mapwriter.Render;
import mapwriter.map.MapView;
import mapwriter.map.mapmode.MapMode;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import Vectron.MWEntityRadar.Proxy.ProxyClient;
import Vectron.MWEntityRadar.utils.Utils;

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
	
	public MultiPlayers(String name, int x, int y, int z, int dimension, double Rotation, boolean loggedout) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
		this.dimension = dimension;
		this.rotation = Rotation;
		this.LoggedOut = loggedout;
	}
	
	public void draw(MapMode mapMode, MapView mapView)
	{
		if (mapView.getDimension() == this.dimension)
		{   
			this.skin = Utils.GetSkin(name);
			
			double scale = mapView.getDimensionScaling(this.dimension);
			Point.Double p = mapMode.getClampedScreenXY(mapView, this.x * scale, this.z * scale);
			this.screenPos.setLocation(p.x + mapMode.xTranslation, p.y + mapMode.yTranslation);
			double arrowSize = mapMode.playerArrowSize  * 0.91; //1.3

			Render.setColour(0xffffffff);

			ProxyClient.mc.renderEngine.bindTexture(skin);
			
			double w = arrowSize; // * 0.7
			double h = arrowSize; //* 0.7
			double zDepth = 0;
			double u1 = 0.125; //0
			double v1 = 0.25; //0
			double u2 = 0.25; //1
			double v2 = 0.5; //1
			
			try {
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		        Tessellator tes = Tessellator.instance;
		        tes.startDrawingQuads();
		       
		        tes.addVertexWithUV(p.x + (w * Math.cos(Math.toRadians(315 + rotation))), p.y + (h * Math.sin(Math.toRadians(315 + rotation))), zDepth, u2, v1);
		        tes.addVertexWithUV(p.x + (w * Math.cos(Math.toRadians(225 + rotation))), p.y + (h * Math.sin(Math.toRadians(225 + rotation))), zDepth, u1, v1);
		        tes.addVertexWithUV(p.x + (w * Math.cos(Math.toRadians(135 + rotation))), p.y + (h * Math.sin(Math.toRadians(135 + rotation))), zDepth, u1, v2);
		        tes.addVertexWithUV(p.x + (w * Math.cos(Math.toRadians( 45 + rotation))), p.y + (h * Math.sin(Math.toRadians( 45 + rotation))), zDepth, u2, v2);
		        
				tes.draw();
				GL11.glDisable(GL11.GL_BLEND);
			} catch (NullPointerException e) {
				MwUtil.log("MwRender.drawTexturedRect: null pointer exception");
			}
		}
	}
}
