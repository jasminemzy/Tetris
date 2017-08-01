package ui;

import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	/**
	 *  窗口标题图片共用的左上角x坐标
	 */
	private final int imgComX;
	
	/**
	 * 标题图片（分数）左上角y坐标
	 */
	private final int imgScoreY;
	
	/**
	 * 标题图片（消行）左上角y坐标
	 */
	private final int imgSwipeLineY;
	
	/**
	 * 经验条左上角y坐标
	 */
	private final int imgExperienceY; 
	
	/**
	 * 分数的最大位数
	 */
	private int maxScoreBit=5;
	
	/**
	 * 消行数的最大位数
	 */
	private int maxSwipeLineBit=5;
	
	/**
	 * 升级所需消行数
	 */
	private static final int LEVEL_UP_REQUIREMENT= GameConfig.getSystemConfig().getLevelUp();
	
	
	
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		
		//初始化标题图片显示的共用x坐标
		imgComX= this.w- Img.NUMBER_W * maxScoreBit - (PADDING<<1);
		
		//初始化分数图片显示的y坐标
		imgScoreY= PADDING;
		
		//初始化消行图片显示的y坐标
		imgSwipeLineY= imgScoreY + Img.SCORE.getHeight(null)+ PADDING;
		
		//初始化经验条图片y坐标
		imgExperienceY= imgSwipeLineY + Img.SWIPE_LINE.getHeight(null) + PADDING;
		
	}
	
	
	
	public void paint(Graphics g) {
		
		this.createWindow(g);
		//窗口标题(分数)
		g.drawImage(Img.SCORE, this.x + PADDING, this.y + imgScoreY, null);
		this.drawNumberLeftPad(imgComX, imgScoreY, this.gameDto.getCurrentPoint(), maxScoreBit, g);
		//窗口标题(消行)
		g.drawImage(Img.SWIPE_LINE, this.x + PADDING, this.y + imgSwipeLineY, null);
		this.drawNumberLeftPad(imgComX, imgSwipeLineY, this.gameDto.getCurrentRemovedLine(), maxSwipeLineBit, g);
		//绘制经验条值槽
		int currentRemovedLine= this.gameDto.getCurrentRemovedLine();
		this.drawRect(imgExperienceY,"下一级", null,(double) (currentRemovedLine % LEVEL_UP_REQUIREMENT) / (double)LEVEL_UP_REQUIREMENT, g);
	}
	
	
	

	
}
