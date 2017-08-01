package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

public abstract class LayerData extends Layer{

	
	/**
	 * 记录最大条数
	 */
	private static final int MAX_ROW =GameConfig.getDataConfig().getMaxRow();

	/**
	 * 记录起始y坐标
	 */
	private static int START_Y = 0;

	/**
	 * 记录间距
	 */
	private static int SPACE = 0;

	/**
	 * 色彩条图片的高度（也即值槽内部高度）
	 */
	private static int RECT_H = Img.COLOR_RECT_H + 4;
		
	
	protected LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		SPACE=(this.h - (Img.COLOR_RECT_H + 4) * 5 - (PADDING<<1) - Img.DB_H) / MAX_ROW;
		START_Y=PADDING + Img.DB_H + SPACE;
		
	}
	
	
	
	/**
	 * 绘制该窗口值槽
	 * 
	 * @param imgTitle 标题图片
	 * @param players 数据源
	 * @param g 画笔
	 */
	public void showData(Image imgTitle, List<Player> players, Graphics g) {
		
		// 绘制标题
		g.drawImage(imgTitle, this.x + PADDING, this.y + PADDING, null);
		// 循环绘制记录
		for (int i = 0; i < MAX_ROW; i++) {
			// 获得一条玩家记录
			Player player = players.get(i);
			// 计算值槽填充比例
			double percent = ((double) this.gameDto.getCurrentPoint()) / player.getPoint();
			// 控制值槽填充比例不超过1
			percent = percent > 1.0 ? 1.0 : percent;
			// 如果分数为0，就只显示NO DATA不显示数据了
			String strPoint= player.getPoint()==0? null: Integer.toString(player.getPoint());
			// 绘制单条记录
			this.drawRect(START_Y + i * (RECT_H + SPACE), player.getName(), strPoint,
					percent, g);
		}
		
	}
	
	
	@Override
	abstract public void paint(Graphics g);
	
}
