package service;

public interface GameService {

	/**
	 * 方向键上
	 */
	public boolean KeyUp();
	
	/**
	 * 方向键下
	 */
	public boolean KeyDown();
	
	/**
	 * 方向键左
	 */
	public boolean KeyLeft();
	
	/**
	 * 方向键右
	 */
	public boolean KeyRight();
	
	/**
	 * 三角
	 */
	public boolean KeyFunctionUp();
	
	/**
	 * 大叉
	 */
	public boolean KeyFunctionDown();
	
	/**
	 * 方块
	 */
	public boolean KeyFunctionLeft();
	
	/**
	 * 圆圈
	 */
	public boolean KeyFunctionRight();
	
	/**
	 * 启动主线程，开始游戏
	 */
	public void startGame();
	
	/**
	 * 游戏主要行为
	 */
	public void mainAction();
	
}
