package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import config.GameConfig;
import entity.GameAct;

public class LayerGame extends Layer {
	

	//方块大小为32*32像素，也即2<<5
	private static final int ACT_SIZE_ROLL=GameConfig.getFrameConfig().getActSizeRol();
	
	/**
	 * 游戏窗口最左边点的x坐标
	 */
	private static final int LEFT_SIDE_X= GameConfig.getSystemConfig().getMinX();
	
	/**
	 * 游戏窗口最右边点的x坐标
	 */
	private static final int RIGHT_SIDE_X= GameConfig.getSystemConfig().getMaxX();
	
	private static final int LOSE_IDX= GameConfig.getFrameConfig().getLoseIndex();
	
	private static final int THIN_BORDER= GameConfig.getFrameConfig().getThinBorder();
	
	
	
	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	
	
	public void paint(Graphics g) {
		
		this.createWindow(g);
		// 获得方块数组集合
		GameAct gameAct= this.gameDto.getGameAct();
		if(gameAct!=null) {
			Point[] actPoints = this.gameDto.getGameAct().getActPoints();
			// 绘制阴影
			this.drawShadow(actPoints, g);
			// 绘制活动方块
			this.drawWholeAct(actPoints, g);
		}
		
		// 绘制游戏地图
		this.drawMap(g);
		// 暂停
		if(this.gameDto.isPause()) {
			drawImageAtCenter(Img.PAUSE, g);
		}
	}
	
	
	
	/**
	 * 绘制活动方块
	 * @param g
	 */
	private void drawWholeAct(Point[] actPoints, Graphics g) {
		
		
		// 获得方块类型编号(0~6)
		int actType = this.gameDto.getGameAct().getActType();
		// 打印运动方块
		for (int i = 0; i < actPoints.length; i++) {
			this.drawActByPoint(actPoints[i].x, actPoints[i].y, actType + 1, g);
		}
	}
	
	
	
	/**
	 * 绘制游戏地图
	 * @param g
	 */
	private void drawMap(Graphics g) {
		
		boolean[][] gameMap = this.gameDto.getGameMap();
		// 计算当前堆积方块的颜色
		int lv = this.gameDto.getcurrentLevel();
		// 让等级1以后显示的ACT颜色在1~7之间循环
		int imgIndex = lv == 0 ? 0 : ((lv - 1) % 7 + 1);
		for (int x = 0; x < gameMap.length; x++) {
			for (int y = 0; y < gameMap[x].length; y++) {
				if (gameMap[x][y]) {
					this.drawActByPoint(x, y, imgIndex, g);
				}
			}
		}
	}
	
	
	
	/**
	 * 根据坐标，还有图片序号画单个方块
	 * @param x 横坐标
	 * @param y	纵坐标
	 * @param imgIdx	 方块图序号 0~8
	 * @param g 画笔
	 */
	private void drawActByPoint(int x, int y, int imgIdx, Graphics g) {
		imgIdx= this.gameDto.isGameStart()? imgIdx:LOSE_IDX;
		g.drawImage(Img.ACT, 
				this.x+ (x<<ACT_SIZE_ROLL)+ THIN_BORDER, 
				this.y+ (y<<ACT_SIZE_ROLL)+ THIN_BORDER, 
				this.x+ ((x+1)<<ACT_SIZE_ROLL)+ THIN_BORDER, 
				this.y+ ((y+1)<<ACT_SIZE_ROLL)+ THIN_BORDER, 
					imgIdx<<ACT_SIZE_ROLL, 0, (imgIdx+1)<<ACT_SIZE_ROLL, 1<<ACT_SIZE_ROLL, null);
	}
	
	
	
	/**
	 * 绘制阴影
	 * 
	 * @param actPoints
	 * @param b
	 */
	private void drawShadow(Point[] actPoints, Graphics g) {
		//TODO p)阴影关闭
		if(!this.gameDto.isShowShadow()) {
			return;
		}
		
		//当前方块的最左点坐标
		int leftX= RIGHT_SIDE_X;
		//当前方块的最右点坐标
		int rightX=LEFT_SIDE_X;
		for(Point point:actPoints) {
			leftX= (int) (leftX<point.getX() ? leftX : point.getX());
			rightX= (int) (rightX>point.getX() ? rightX : point.getX());
		}
		
		g.setColor(Color.WHITE);
		g.drawImage(Img.SHADOW, 
				this.x+8+(leftX<<ACT_SIZE_ROLL), 
				this.y+8 , 
				((rightX-leftX+1)<<ACT_SIZE_ROLL), 
				this.h-(8<<1), null);
		
		
	}
	
}
