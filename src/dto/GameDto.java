package dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import config.GameConfig;
import entity.GameAct;

public class GameDto {
	
	private static final int GAMEZONE_W= GameConfig.getSystemConfig().getMaxX()-GameConfig.getSystemConfig().getMinX() +1;
	
	private static final int GAMEZONE_H= GameConfig.getSystemConfig().getMaxY()-GameConfig.getSystemConfig().getMinY() +1;
	
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
	 * 构造函数
	 */
	public GameDto() {
		gameDtoInit();
	}
	
	
	/**
	 * DTO初始化
	 */
	public void gameDtoInit() {
		this.gameMap= new boolean[GAMEZONE_W][GAMEZONE_H];
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
	
	
	
	
}
