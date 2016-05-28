package Vectron.MWEntityRadar.Overlay;

import mapwriter.api.ILabelInfo;

public class LabelInfo implements ILabelInfo
{
	private String[] text;

	public LabelInfo(String[] text)
	{
		this.setInfoText(text);
	}

	@Override
	public String[] getInfoText()
	{
		return this.text;
	}

	public void setInfoText(String[] text)
	{
		this.text = text;
	}
}
