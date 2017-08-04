package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.GameConfig;
import entity.GameAct;
import util.GameFunction;

public class GameDto {
	
	/**
	 * 游戏区域宽度
	 */
	public static final int GAMEZONE_W= GameConfig.getSystemConfig().getMaxX()-GameConfig.getSystemConfig().getMinX() +1;
	
	/**
	 * 游戏区域高度
	 */
	public static final int GAMEZONE_H= GameConfig.getSystemConfig().getMaxY()-GameConfig.getSystemConfig().getMinY() +1;
	
	/**
	 * 数据库记录
	 */
	private List<Player> dbRecord;
	
	/**
	 * 本地记录
	 */
	private List<Player> diskRecord;
	
	/**
	 * 游戏地图
	 */
	private boolean[][] gameMap;
	
	/**
	 * 正在下落方块
	 */
	private GameAct gameAct;
	
	/**
	 * 下一个方块序号
	 */
	private int next;
	
	/**
	 * 等级
	 */
	private int currentLevel;
	
	/**
	 * 当前分数
	 */
	private int currentPoint;
	
	/**
	 * 当前已消行数
	 */
	private int currentRemovedLine;
	
	/**
	 * 线程睡眠时间(方块下落速度)
	 */
	private long sleepTime;
	
	public long getSleepTime() {
		return sleepTime;
	}

	/**
	 * 游戏是否是开始状态
	 */
	private boolean gameStart;
	
	/**
	 * 是否显示方块背后阴影
	 */
	private boolean showShadow;
	
	/**
	 * 暂停状态
	 */
	private boolean pause;
	
	/**
	 * 是否允许使用作弊键
	 */
	private boolean cheat;



	/**
	 * 构造函数
	 */
	public GameDto() {
		gameDtoInit();
	}
	
	
	/**
	 * DTO初始化
	 */
	public void gameDtoInit() {
		//清除游戏地图
		this.gameMap= new boolean[GAMEZONE_W][GAMEZONE_H];
		//默认显示阴影
		this.showShadow=true;
		this.pause=false;
		this.cheat=false;
		//初始化分数
		this.currentLevel=0;
		this.currentPoint=0;
		this.currentRemovedLine=0;
		this.sleepTime=GameFunction.getSleepTimeByLevel(this.currentLevel);
		//TODO p)初始化所有游戏对象
	}
	
	
	



	private void setFilledRecord(List<Player> players) {
		//如果进来的是空列表，那就创建
		if (players == null) {
			players = new ArrayList<Player>();
		}
		//如果记录数小于5，那就加到5为止
		while (players.size() < 5) {
			players.add(new Player("No Data", 0));
		}
		// return players;
	}
	
	
	
	public List<Player> getDbRecord() {
		return dbRecord;
	}

	public void setDbRecord(List<Player> dbRecord) {
		setFilledRecord(dbRecord);
		Collections.sort(dbRecord);
		this.dbRecord = dbRecord;
	}

	public List<Player> getDiskRecord() {
		return diskRecord;
	}

	public void setDiskRecord(List<Player> diskRecord) {
		setFilledRecord(diskRecord);
		Collections.sort(diskRecord);
		this.diskRecord = diskRecord;
	}

	public boolean[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public GameAct getGameAct() {
		return gameAct;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getcurrentLevel() {
		return currentLevel;
	}

	public void setcurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
		this.sleepTime=GameFunction.getSleepTimeByLevel(this.currentLevel);
	}

	public int getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(int currentPoint) {
		this.currentPoint = currentPoint;
	}

	public int getCurrentRemovedLine() {
		return currentRemovedLine;
	}

	public void setCurrentRemovedLine(int currentRemovedLine) {
		this.currentRemovedLine = currentRemovedLine;
	}
	
	public boolean isGameStart() {
		return gameStart;
	}
	
	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}
	
	public boolean isShowShadow() {
		return showShadow;
	}
	
	public void reverseShowShadow() {
		this.showShadow = !this.showShadow;
	}
	
	public boolean isPause() {
		return pause;
	}
	
	public void reversePause() {
		this.pause = !this.pause;
	}
	
	public boolean isCheat() {
		return cheat;
	}

	public void setCheat(boolean cheat) {
		this.cheat = cheat;
	}
}
