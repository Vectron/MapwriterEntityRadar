package Vectron.MWEntityRadar.utils;

import org.lwjgl.opengl.GL11;

import com.mojang.util.UUIDTypeAdapter;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;

public class Utils
{
	public static ResourceLocation GetSkin(String name)
	{
		ResourceLocation resourcelocation = AbstractClientPlayer.getLocationSkin(name);

		if (resourcelocation == null)
		{
			resourcelocation = DefaultPlayerSkin.getDefaultSkin(
					UUIDTypeAdapter.fromString("Steve"));
		}

		AbstractClientPlayer.getDownloadImageSkin(resourcelocation, name);

		return resourcelocation;
	}

	/**
	 * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't
	 * used anywhere in vanilla code.
	 */
	public static void drawRotatingScaledCustomSizeModalRect(double x, double y, double u, double v,
			double uWidth, double vHeight, double width, double height, double tileWidth,
			double tileHeight, double rotation, double zDepth)
	{
		double f = 1.0F / tileWidth;
		double f1 = 1.0F / tileHeight;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer
				.pos(
						x + (width * Math.cos(Math.toRadians(315 + rotation))),
						y + (height * Math.sin(Math.toRadians(315 + rotation))),
						zDepth)
				.tex((double) (u * f), (double) ((v + (float) vHeight) * f1))
				.endVertex();
		vertexbuffer
				.pos(
						x + (width * Math.cos(Math.toRadians(225 + rotation))),
						y + (height * Math.sin(Math.toRadians(225 + rotation))),
						zDepth)
				.tex((double) ((u + (float) uWidth) * f), (double) ((v + (float) vHeight) * f1))
				.endVertex();
		vertexbuffer
				.pos(
						x + (width * Math.cos(Math.toRadians(135 + rotation))),
						y + (height * Math.sin(Math.toRadians(135 + rotation))),
						zDepth)
				.tex((double) ((u + (float) uWidth) * f), (double) (v * f1))
				.endVertex();
		vertexbuffer
				.pos(
						x + (width * Math.cos(Math.toRadians(45 + rotation))),
						y + (height * Math.sin(Math.toRadians(45 + rotation))),
						zDepth)
				.tex((double) (u * f), (double) (v * f1))
				.endVertex();
		tessellator.draw();
	}
}
