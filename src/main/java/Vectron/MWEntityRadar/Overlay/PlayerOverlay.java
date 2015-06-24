package Vectron.MWEntityRadar.Overlay;

import java.util.ArrayList;

import mapwriter.api.IMwChunkOverlay;
import mapwriter.api.IMwDataProvider;
import mapwriter.map.MapView;
import mapwriter.map.mapmode.MapMode;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Point;

import Vectron.MWEntityRadar.MWEntityRadar;
import Vectron.MWEntityRadar.Proxy.ProxyClient;
import Vectron.MWEntityRadar.Radar.MultiPlayers;
import Vectron.MWEntityRadar.Radar.MultiplayerManager;

public class PlayerOverlay implements IMwDataProvider {

	private static PlayerOverlay _instance;
	
	public static PlayerOverlay instance(){
		if(_instance == null)
			_instance = new PlayerOverlay();			
		return _instance;
	}	
	private MapMode lMapMode =null;
	//private MapView lMapView = null;
	
	
	@Override
	public ArrayList<IMwChunkOverlay> getChunksOverlay(int dim, double centerX,
			double centerZ, double minX, double minZ, double maxX, double maxZ) {
		return null;
	}

	@Override
	public String getStatusString(int dim, int bX, int bY, int bZ) {
		return "";
	}

	@Override
	public void onMiddleClick(int dim, int bX, int bZ, MapView mapview) {	
	}

	@Override
	public void onDimensionChanged(int dimension, MapView mapview) {
		
	}

	@Override
	public void onMapCenterChanged(double vX, double vZ, MapView mapview) {

	}

	@Override
	public void onZoomChanged(int level, MapView mapview) {
		
	}

	@Override
	public void onOverlayActivated(MapView mapview) {
		MultiplayerManager.getInstance().InitList();
	}

	@Override
	public void onOverlayDeactivated(MapView mapview) {
	}

	@Override
	public void onDraw(MapView mapview, MapMode mapmode) {
		this.lMapMode = mapmode;
		MultiplayerManager.getInstance().updateMPPlayers();
		MultiplayerManager.getInstance().DrawArrows(mapmode, mapview);
		DrawGui();
	}

	@Override
	public boolean onMouseInput(MapView mapview, MapMode mapmode) {	
		return false;
	}
	
	private void DrawGui()
	{		
		if (lMapMode instanceof mapwriter.map.mapmode.FullScreenMapMode)
		{
	        ScaledResolution scaledresolution = new ScaledResolution(ProxyClient.mc, ProxyClient.mc.displayWidth, ProxyClient.mc.displayHeight);
	        int w = scaledresolution.getScaledWidth();
	        int h = scaledresolution.getScaledHeight();
	        Point Mousepoint =  new Point((Mouse.getX() * w) / ProxyClient.mc.displayWidth, h - (Mouse.getY() * h) / ProxyClient.mc.displayHeight - 1);
		    
			MultiPlayers playeratmouse = MultiplayerManager.getInstance().getPlayersNearScreenPos(Mousepoint.getX(),Mousepoint.getY());
			
			if (playeratmouse != null){
				if (ProxyClient.mc.currentScreen instanceof mapwriter.gui.MwGui)
				{

				    int xstart = Mousepoint.getX() -(w/2);
				    int ystart = Mousepoint.getY() -(h/2);
					((mapwriter.gui.MwGui)ProxyClient.mc.currentScreen).drawMouseOverHint(xstart, ystart, playeratmouse.name, playeratmouse.x, playeratmouse.y, playeratmouse.z);
				}
			}
		}	
	}
}
