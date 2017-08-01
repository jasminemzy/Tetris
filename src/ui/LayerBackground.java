package ui;

import java.awt.Graphics;

public class LayerBackground extends Layer {


	
	public LayerBackground(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		int bgIndex=(this.gameDto.getcurrentLevel()) % Img.BG_LIST.size();
		g.drawImage(Img.BG_LIST.get(bgIndex), 0, 0, 1168, 740, null);
	}

}
