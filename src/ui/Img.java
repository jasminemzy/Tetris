package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import config.GameConfig;

public class Img {
	
	/**
	 * 窗口图片
	 */
	public static Image WINDOW= new ImageIcon("Graphics/Window/window1.png").getImage();
	
	/**
	 * 窗口图片宽度
	 */
	public static int WINDOW_W=WINDOW.getWidth(null);
	
	/**
	 * 窗口图片高度
	 */
	public static int WINDOW_H=WINDOW.getHeight(null);
	
	/**
	 * 数字图片 240*200； 5行0~9数字; 单个为24*40
	 */
	public static Image NUMBER = new ImageIcon("Graphics/String/digit.png").getImage();
	/**
	 * 数字切片的宽度
	 */
	public static int NUMBER_W = NUMBER.getWidth(null) / 10;

	/**
	 * 数字切片的高度
	 */
	public static int NUMBER_H = NUMBER.getHeight(null) / 5;
	
	/**
	 * 彩色矩形值槽
	 */
	public static Image COLOR_RECT= new ImageIcon("Graphics/Window/colorRect.png").getImage();
	
	/**
	 * 值槽色彩条图片宽度
	 */
	public static int COLOR_RECT_W= COLOR_RECT.getWidth(null);
	
	/**
	 * 值槽色彩条图片高度
	 */
	public static int COLOR_RECT_H= COLOR_RECT.getHeight(null);
	
	/**
	 * 签名图片
	 */
	public static Image ABOUT = new ImageIcon("Graphics/String/about.png").getImage();
	
	/**
	 * 数据库文字图片
	 */
	public static Image DB= new ImageIcon("Graphics/String/db.png").getImage();
	
	/**
	 * 本地记录文字高度
	 */
	public static int DB_H= COLOR_RECT.getHeight(null);
	
	/**
	 * 本地记录文字图片
	 */
	public static Image DISK= new ImageIcon("Graphics/String/localRecord.png").getImage();
	
	/**
	 * 运动方块图片
	 */
	public static Image ACT= new ImageIcon("Graphics/Game/rect.png").getImage();
	
	/**
	 * 等级文字图片
	 */
	public static Image LEVEL = new ImageIcon("Graphics/String/level.png").getImage();
	
	/**
	 * 等级文字图片的宽度
	 */
	public static int LEVEL_W = LEVEL.getWidth(null);
	
	/**
	 * 下一方块图片
	 */
	public static Image[] NEXT_ACT;

	/**
	 * 窗口标题图片（分数）
	 */
	public static Image SCORE = new ImageIcon("Graphics/String/score.png").getImage();
	
	
	/**
	 * 窗口标题图片（消行）
	 */
	public static Image SWIPE_LINE = new ImageIcon("Graphics/String/swipeLine.png").getImage();
	
	/**
	 * 半透明阴影图片
	 */
	public static Image SHADOW= new ImageIcon("Graphics/Game/shadow.png").getImage();
	
	/**
	 * 开始按钮
	 */
	public static Icon BTN_START= new ImageIcon("Graphics/String/start.png");
	
	/**
	 * 设置按钮
	 */
	public static Icon BTN_CONFIG= new ImageIcon("Graphics/String/config.png");
	
	
	/**
	 * 背景图片列表
	 */
	public static List<Image> BG_LIST;
	
	
	
	static {
		//下一个方块图片数组
		NEXT_ACT= new Image[GameConfig.getSystemConfig().getActTypeConfig().size()];
		for(int i=0;i< NEXT_ACT.length; i++) {
			NEXT_ACT[i]= new ImageIcon("Graphics/Game/"+ i +".png").getImage();
		}
		
		//背景图片数组
		File dir= new File("Graphics/Background");
		File[] files= dir.listFiles();
		BG_LIST= new ArrayList<Image>();
		for(File file: files) {
			if(!file.isDirectory()) {
				BG_LIST.add(new ImageIcon(file.getPath()).getImage());
			}
		}
		
	}
	
	private Img() {
		
	}
}
