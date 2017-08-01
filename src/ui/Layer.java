package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.DataConfig;
import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

/*
 * 绘制窗口
 */
abstract public class Layer {
	
	protected static final int PADDING; 
	protected static final int BORDER;
	static {
		//获得游戏配置
		FrameConfig fCfg= GameConfig.getFrameConfig();
		PADDING=fCfg.getPadding();
		BORDER=fCfg.getBorder();		
	}
	
	
	
	/**
	 * 值槽宽度
	 */
	private final int rectW;
	
	/**
	 * 默认字体
	 */
	private static final Font FONT= new Font("黑体", Font.BOLD, 20);
	

	
	//窗口左上角x坐标
	protected int x;
	//窗口左上角y坐标
	protected int y;
	//窗口宽度
	protected int w;
	//窗口高度
	protected int h;
	
	protected GameDto gameDto= null;
	


	protected  Layer(int x, int y, int w, int h) {
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.rectW= this.w - (PADDING<<1);
		
	}
	
	/*
	 * 绘制窗口
	 */
	protected void createWindow(Graphics g) {
		//左上
		g.drawImage(Img.WINDOW, x, y, x+BORDER, y+BORDER, 0, 0, BORDER, BORDER, null);
		//中上
		g.drawImage(Img.WINDOW, x+BORDER, y, x+w-BORDER, y+BORDER, BORDER, 0, Img.WINDOW_W-BORDER, BORDER, null);
		//右上
		g.drawImage(Img.WINDOW, x+w-BORDER, y, x+w, y+BORDER, Img.WINDOW_W - BORDER, 0, Img.WINDOW_W, BORDER, null);
		//左中
		g.drawImage(Img.WINDOW, x, y+BORDER, x+BORDER, y+h-BORDER, 0, BORDER, BORDER, Img.WINDOW_H-BORDER, null);
		//中中
		g.drawImage(Img.WINDOW, x+BORDER, y+BORDER, x+w-BORDER, y+h-BORDER, BORDER, BORDER, Img.WINDOW_W-BORDER, Img.WINDOW_H-BORDER, null);
		//右中
		g.drawImage(Img.WINDOW, x+w-BORDER, y+BORDER, x+w, y+h-BORDER, Img.WINDOW_W-BORDER, BORDER, Img.WINDOW_W, Img.WINDOW_H-BORDER, null);
		//左下
		g.drawImage(Img.WINDOW, x, y+h-BORDER, x+BORDER, y+h, 0, Img.WINDOW_H-BORDER, BORDER, Img.WINDOW_H, null);
		//中下
		g.drawImage(Img.WINDOW, x+BORDER, y+h-BORDER, x+w-BORDER, y+h, BORDER, Img.WINDOW_H-BORDER, Img.WINDOW_W-BORDER, Img.WINDOW_H, null);
		//右下
		g.drawImage(Img.WINDOW, x+w-BORDER, y+h-BORDER, x+w, y+h, Img.WINDOW_W-BORDER, Img.WINDOW_H-BORDER, Img.WINDOW_W, Img.WINDOW_H, null);	
	}
	
	
	
	public void setGameDto(GameDto gameDto) {
		this.gameDto = gameDto;
	}
	
	
	
	/*
	 * 刷新游戏具体内容
	 * @param g 
	 */
	abstract public void paint(Graphics g);
	
	
	
	/**
	 * 在正中绘制图像
	 */
	protected void drawImageAtCenter(Image image, Graphics g) {
		int imgW= image.getWidth(null);
		int imgH= image.getHeight(null);
		int imgX= this.x+ (this.w- imgW >>1);
		int imgY= this.y+ (this.h- imgH >>1);
		g.drawImage(image, imgX, imgY, null);
		
	}
	
	
	
	/**
	 * 左对齐绘制数字图片
	 * 
	 * @param number
	 *            显示的数字
	 * @param x
	 *            左上角x坐标
	 * @param y
	 *            左上角y坐标
	 * @param g
	 */
	protected void drawNumber(int x, int y, int number, Graphics g) {
		// 把数字number中的每一位取出
		String strNum = Integer.toString(number);
		for (int i = 0; i < strNum.length(); i++) {
			int digit = strNum.charAt(i) - '0';
			g.drawImage(Img.NUMBER, this.x + x + Img.NUMBER_W * i, this.y + y, this.x + x + Img.NUMBER_W * (i + 1),
					this.y + y + Img.NUMBER_H, digit * Img.NUMBER_W, 0, (digit + 1) * Img.NUMBER_W, Img.NUMBER_H, null);
		}
	}

	/**
	 * 右对齐绘制数字（也即左填充）
	 * 
	 * @param x
	 *            数字左上角x坐标
	 * @param y
	 *            数字左上角y坐标
	 * @param number
	 *            要绘制的数字
	 * @param maxBit
	 *            右对齐所允许的最大数字位数
	 * @param g
	 */
	protected void drawNumberLeftPad(int x, int y, int number, int maxBit, Graphics g) {
		// 把数字number中的每一位取出
		String strNum = Integer.toString(number);
		// 循环绘制数字右对齐
		for (int i = 0; i < maxBit; i++) {
			// 判断是否满足绘制条件（空出前面没有数字的位置）
			if (maxBit - i <= strNum.length()) {
				// 获得数字在字符串中的第几个
				int idx = i - maxBit + strNum.length();
				// 将改位具体对应的数字取出
				int digit = strNum.charAt(idx) - '0';
				// 绘制数字
				g.drawImage(Img.NUMBER, this.x + x + Img.NUMBER_W * i, this.y + y, this.x + x + Img.NUMBER_W * (i + 1),
						this.y + y + Img.NUMBER_H, digit * Img.NUMBER_W, Img.NUMBER_H, (digit + 1) * Img.NUMBER_W, Img.NUMBER_H << 1, null);
			}
		}
	}
	
	
	

	/**
	 * 绘制值槽
	 * @param y 值槽绘制左上角起点y坐标
	 * @param title 值槽内部要绘制的文字
	 * @param number 值槽内要绘制的数字
	 * @param value 值槽填充比例分子
	 * @param maxValue 值槽填充比例分母
	 * @param g
	 */
	protected void drawRect(int y, String title, String number, double percent, Graphics g) {
		//绘制起点初始化
		int rectX=this.x + PADDING;
		int rectY=this.y + y;
		//绘制背景
		g.setColor(Color.WHITE);
		g.fillRect(rectX, rectY, rectW, Img.COLOR_RECT_H + 4);
		g.setColor(Color.BLACK);
		g.fillRect(rectX +1 , rectY +1 , rectW-2, Img.COLOR_RECT_H + 2);
		g.setColor(Color.WHITE);
		g.fillRect(rectX + 2, rectY +2 , rectW-4, Img.COLOR_RECT_H);
		g.setColor(Color.GREEN);
		//绘制值槽
		//计算宽度
		int w= (int) (percent * (rectW-4));
		//求出颜色在色彩图里的x偏移量
		int colorIdx= (int) (Img.COLOR_RECT_W * percent) -1 ;
		g.drawImage(Img.COLOR_RECT,
				rectX + 2, rectY +2, 
				rectX + 2 + w, rectY +2 +Img.COLOR_RECT_H, 
				colorIdx, 0, colorIdx+1, Img.COLOR_RECT_H, null);
		//绘制文字
		g.setColor(Color.BLACK);
		g.setFont(FONT);
		g.drawString(title, rectX+4, rectY + 24);
		if(number !=null) {
			g.drawString(number, rectX+ 220, rectY + 24);
		}
	}
	
}
