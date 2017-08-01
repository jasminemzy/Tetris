package ui;

import java.awt.Graphics;

public class LayerLevel extends Layer {

	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// 窗口标题
		g.drawImage(Img.LEVEL, this.x + PADDING, this.y + PADDING, null);
		// 显示等级
		drawNumberLeftPad(76, 64, this.gameDto.getcurrentLevel(), 2, g);

	}

	
}
