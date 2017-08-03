package service;

import java.util.List;

import dto.Player;

public interface GameService {

	/**
	 * 方向键上
	 */
	public void KeyUp();
	
	/**
	 * 方向键下
	 */
	public void KeyDown();
	
	/**
	 * 方向键左
	 */
	public void KeyLeft();
	
	/**
	 * 方向键右
	 */
	public void KeyRight();
	
	/**
	 * 三角
	 */
	public void KeyFunctionUp();
	
	/**
	 * 大叉
	 */
	public void KeyFunctionDown();
	
	/**
	 * 方块
	 */
	public void KeyFunctionLeft();
	
	/**
	 * 圆圈
	 */
	public void KeyFunctionRight();
	
	/**
	 * 设置数据库数据对象
	 * @param players
	 */
	public void setDbRecord(List<Player> players);
	
	/**
	 * 设置本地磁盘数据对象
	 * @param players
	 */
	public void setDiskRecord(List<Player> players);

	/**
	 * 启动主线程，开始游戏
	 */
	public void startMainThread();
	
	
}
