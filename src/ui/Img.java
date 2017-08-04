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
	 * 图片路径
	 */
	public static final String GRAPHICS_PATH= "Graphics";
	
	/**
	 * 窗口图片
	 */
	public static Image WINDOW= null;
	
	/**
	 * 窗口图片宽度
	 */
	public static int WINDOW_W= 0;
	
	/**
	 * 窗口图片高度
	 */
	public static int WINDOW_H=0;
	
	/**
	 * 数字图片 240*200； 5行0~9数字; 单个为24*40
	 */
	public static Image NUMBER = null;
	/**
	 * 数字切片的宽度
	 */
	public static int NUMBER_W = 0;

	/**
	 * 数字切片的高度
	 */
	public static int NUMBER_H = 0;
	
	/**
	 * 彩色矩形值槽
	 */
	public static Image COLOR_RECT= null;
	
	/**
	 * 值槽色彩条图片宽度
	 */
	public static int COLOR_RECT_W= 0;
	
	/**
	 * 值槽色彩条图片高度
	 */
	public static int COLOR_RECT_H= 0;
	
	/**
	 * 签名图片
	 */
	public static Image ABOUT = null;
	
	/**
	 * 数据库文字图片
	 */
	public static Image DB= null;
	
	/**
	 * 本地记录文字高度
	 */
	public static int DB_H= 0;
	
	/**
	 * 本地记录文字图片
	 */
	public static Image DISK= null;
	
	/**
	 * 运动方块图片
	 */
	public static Image ACT= null;
	
	/**
	 * 等级文字图片
	 */
	public static Image LEVEL = null;
	
	/**
	 * 等级文字图片的宽度
	 */
	public static int LEVEL_W = 0;
	
	/**
	 * 下一方块图片
	 */
	public static Image[] NEXT_ACT= null;

	/**
	 * 窗口标题图片（分数）
	 */
	public static Image SCORE = null;
	
	
	/**
	 * 窗口标题图片（消行）
	 */
	public static Image SWIPE_LINE = null;
	
	/**
	 * 半透明阴影图片
	 */
	public static Image SHADOW= null;
	
	/**
	 * 暂停文字图片
	 */
	public static Image PAUSE= null;
	
	/**
	 * 开始按钮
	 */
	public static ImageIcon BTN_START= null;
	
	/**
	 * 设置按钮
	 */
	public static ImageIcon BTN_CONFIG= null;
	
	/**
	 * 背景图片列表
	 */
	public static List<Image> BG_LIST= null;
	
	
	static {
		setSkin("skin1");
	}
	
	
	public static void setSkin(String path) {
		String skinPath= GRAPHICS_PATH+"/"+path;
		// 窗口图片
		WINDOW= new ImageIcon(skinPath +  "/Window/window1.png").getImage();
		// 窗口图片宽度
		WINDOW_W=WINDOW.getWidth(null);
		// 窗口图片高度
		WINDOW_H=WINDOW.getHeight(null);
		// 数字图片 240*200； 5行0~9数字; 单个为24*40
		NUMBER = new ImageIcon(skinPath + "/String/digit.png").getImage();
		// 数字切片的宽度
		NUMBER_W = NUMBER.getWidth(null) / 10;
		// 数字切片的高度
		NUMBER_H = NUMBER.getHeight(null) / 5;
		// 彩色矩形值槽
		COLOR_RECT= new ImageIcon(skinPath + "/Window/colorRect.png").getImage();
		// 值槽色彩条图片宽度
		COLOR_RECT_W= COLOR_RECT.getWidth(null);
		// 值槽色彩条图片高度
		COLOR_RECT_H= COLOR_RECT.getHeight(null);
		// 签名图片
		ABOUT = new ImageIcon(skinPath + "/String/about.png").getImage();
		// 数据库文字图片
		DB= new ImageIcon(skinPath + "/String/db.png").getImage();
		// 本地记录文字高度
		DB_H= COLOR_RECT.getHeight(null);
		// 本地记录文字图片
		DISK= new ImageIcon(skinPath + "/String/localRecord.png").getImage();
		// 运动方块图片
		ACT= new ImageIcon(skinPath + "/Game/rect.png").getImage();
		// 等级文字图片
		LEVEL = new ImageIcon(skinPath + "/String/level.png").getImage();
		// 等级文字图片的宽度
		LEVEL_W = LEVEL.getWidth(null);
		// 窗口标题图片（分数）
		SCORE = new ImageIcon(skinPath + "/String/score.png").getImage();
		// 窗口标题图片（消行）
		SWIPE_LINE = new ImageIcon(skinPath + "/String/swipeLine.png").getImage();
		// 半透明阴影图片
		SHADOW= new ImageIcon(skinPath + "/Game/shadow.png").getImage();
		// 暂停文字图片
		PAUSE= new ImageIcon(skinPath + "/String/palse.png").getImage();
		// 开始按钮
		BTN_START= new ImageIcon(skinPath + "/String/start.png");
		// 设置按钮
		BTN_CONFIG= new ImageIcon(skinPath + "/String/config.png");
		// 下一个方块图片数组
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getActTypeConfig().size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon(skinPath + "/Game/" + i + ".png").getImage();
		}
		// 背景图片数组
		File dir = new File(skinPath + "/Background");
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<Image>();
		for (File file : files) {
			if (!file.isDirectory() && !file.isHidden()) {
				BG_LIST.add(new ImageIcon(file.getPath()).getImage());
			}
		}
	}
	
	
	
	private Img() {
		
	}
}
