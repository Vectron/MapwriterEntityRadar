package Vectron.MWEntityRadar.Overlay;

import java.util.ArrayList;

import Vectron.MWEntityRadar.Radar.MultiPlayers;
import Vectron.MWEntityRadar.Radar.MultiplayerManager;
import mapwriter.api.ILabelInfo;
import mapwriter.api.IMapMode;
import mapwriter.api.IMapView;
import mapwriter.api.IMwChunkOverlay;
import mapwriter.api.IMwDataProvider;

public class PlayerOverlay implements IMwDataProvider
{
	private static PlayerOverlay _instance;

	public static PlayerOverlay instance()
	{
		if (_instance == null)
			_instance = new PlayerOverlay();
		return _instance;
	}

	private IMapMode lMapMode = null;
	// private MapView lMapView = null;

	@Override
	public ArrayList<IMwChunkOverlay> getChunksOverlay(int dim, double centerX, double centerZ,
			double minX, double minZ, double maxX, double maxZ)
	{
		return null;
	}

	@Override
	public String getStatusString(int dim, int bX, int bY, int bZ)
	{
		return "";
	}

	@Override
	public void onMiddleClick(int dim, int bX, int bZ, IMapView mapview)
	{
	}

	@Override
	public void onDimensionChanged(int dimension, IMapView mapview)
	{

	}

	@Override
	public void onMapCenterChanged(double vX, double vZ, IMapView mapview)
	{

	}

	@Override
	public void onZoomChanged(int level, IMapView mapview)
	{

	}

	@Override
	public void onOverlayActivated(IMapView mapview)
	{
		MultiplayerManager.getInstance().InitList();
	}

	@Override
	public void onOverlayDeactivated(IMapView mapview)
	{
	}

	@Override
	public void onDraw(IMapView mapview, IMapMode mapmode)
	{
		this.lMapMode = mapmode;
		MultiplayerManager.getInstance().updateMPPlayers();
		MultiplayerManager.getInstance().DrawArrows(mapmode, mapview);
	}

	@Override
	public boolean onMouseInput(IMapView mapview, IMapMode mapmode)
	{
		return false;
	}

	@Override
	public ILabelInfo getLabelInfo(int mouseX, int mouseY)
	{
		ILabelInfo labelInfo = null;
		MultiPlayers playeratmouse = MultiplayerManager.getInstance().getPlayersNearScreenPos(
				mouseX,
				mouseY);

		if (playeratmouse != null)
		{
			labelInfo = new LabelInfo(new String[]
			{
					playeratmouse.name,
					String.format("(%d, %d, %d)", playeratmouse.x, playeratmouse.y, playeratmouse.z)
			});

		}
		return labelInfo;
	}
}
