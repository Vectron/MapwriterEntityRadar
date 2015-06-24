package Vectron.MWEntityRadar.utils;

import java.util.Map;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.properties.Property;

public class Utils 
{
	public static ResourceLocation GetSkin(String name)
	{
		GameProfile gameprofile = new GameProfile((UUID)null, name);;
        
		ResourceLocation resourcelocation = AbstractClientPlayer.locationStevePng;

        if (gameprofile != null)
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            Map map = minecraft.getSkinManager().loadSkinFromCache(gameprofile);

            if (map.containsKey(Type.SKIN))
            {
                resourcelocation = minecraft.getSkinManager().loadSkin((MinecraftProfileTexture)map.get(Type.SKIN), Type.SKIN);
            }
        }
        
		return resourcelocation;
	}
}
