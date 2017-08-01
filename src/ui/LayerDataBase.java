package ui;

import java.awt.Graphics;

public class LayerDataBase extends LayerData{
	
	
	
	
	public LayerDataBase(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		this.showData(Img.DB, this.gameDto.getDbRecord(), g);
	}
	
}
