package ui;

import java.awt.Graphics;

public class LayerAbout extends Layer {
	

	
	
	public LayerAbout(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		drawImageAtCenter(Img.ABOUT, g);
	}
}
