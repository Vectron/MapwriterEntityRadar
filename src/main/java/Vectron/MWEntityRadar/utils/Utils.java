package Vectron.MWEntityRadar.utils;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

public class Utils 
{
	public static ResourceLocation GetSkin(String Name)
	{
	    ResourceLocation resourcelocation = AbstractClientPlayer.getLocationSkin(Name);

	        if (resourcelocation == null)
	        {
	            resourcelocation = AbstractClientPlayer.getLocationSkin("default");
	        }
		
		AbstractClientPlayer.getDownloadImageSkin(resourcelocation, Name);
        
        return resourcelocation;
	}
}
