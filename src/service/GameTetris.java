package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService {
	
	private GameDto gameDto;
	
	/**
	 * 随机数生成器
	 */
	private Random random=new Random();
	
	/**
	 * 方块种类数
	 */
	private static final int MAX_TYPE= GameConfig.getSystemConfig().getActTypeConfig().size();
	
	/**
	 * 升级所需消行数
	 */
	private static final int LEVEL_UP_REQUIREMENT= GameConfig.getSystemConfig().getLevelUp();
	
	private static final Map<Integer, Integer> PLUS_POINT_MAP= GameConfig.getSystemConfig().getPlusPointMap();
	
	
	
	public GameTetris(GameDto gameDto) {
		this.gameDto=gameDto;
	}
	
	
	
	/**
	 * 方块操作（上）
	 */
	@Override
	public boolean KeyUp() {
		if(this.gameDto.isPause()) {
			return true;
		}
		synchronized(this.gameDto) {
			this.gameDto.getGameAct().rotateAct(this.gameDto.getGameMap());			
		}
		return true;
	}



	/**
	 * 方块操作（下）
	 */
	@Override
	public boolean KeyDown() {
		if(this.gameDto.isPause()) {
			return false;
		}
		synchronized (this.gameDto) {
			if(!gameDto.isGameStart()) {
				return false;
			}
			// 执行向下降落操作，如果可以移动就结束
			if (this.gameDto.getGameAct().moveAct(0, 1, this.gameDto.getGameMap())) {
				return false;
			}
			// 如果不可继续向下移动，执行下列额外操作
			// 获得游戏地图对象
			boolean[][] gameMap = this.gameDto.getGameMap();
			// 获得方块对象
			Point[] gameAct = this.gameDto.getGameAct().getActPoints();
			// 将方块堆积到地图数组
			for (int i = 0; i < gameAct.length; i++) {
				gameMap[gameAct[i].x][gameAct[i].y] = true;
			}

			// 执行消行，并返回此次共消了多少行
			int removedLineCount = this.removeLines();
			// 更新等级、总消行数、总分数
			if (removedLineCount > 0) {
				this.updateDtoExpInformation(removedLineCount);
			}

			// 在顶部创建新的下一个方块
			this.gameDto.getGameAct().init(this.gameDto.getNext());
			// 随机生成再下一个方块
			this.gameDto.setNext(random.nextInt(MAX_TYPE - 1));
			// 检查游戏是否失败
			if (this.isLose()) {
				this.gameDto.setGameStart(false);
			}
		}
		return true;
	}
	
	
	
	/**
	 * 方块操作（左）
	 */
	@Override
	public boolean KeyLeft() {
		if(this.gameDto.isPause()) {
			return true;
		}
		synchronized(this.gameDto) {
			this.gameDto.getGameAct().moveAct(-1, 0, this.gameDto.getGameMap());
		}
		return true;
	}



	/**
	 * 方块操作（右）
	 */
	@Override
	public boolean KeyRight() {
		if(this.gameDto.isPause()) {
			return true;
		}
		synchronized(this.gameDto) {
			this.gameDto.getGameAct().moveAct(1, 0,this.gameDto.getGameMap());
		}
		return true;
	}



	/* 
	 * 作弊键
	 */
	@Override
	public boolean KeyFunctionUp() {
		synchronized(this.gameDto) {
			this.updateDtoExpInformation(4);
		}
		return true;
	}



	/* 
	 * 大叉键
	 * 瞬间下落
	 */
	@Override
	public boolean KeyFunctionDown() {
		if(this.gameDto.isPause()) {
			return true;
		}
		//避免游戏未开始时按功能下键报空指针错
		if(gameDto.isGameStart()) {
			while(!this.KeyDown()) {}
		}
		return true;
	}



	@Override
	public boolean KeyFunctionLeft() {
		//反转阴影设置
		if(gameDto.isGameStart()) {
			this.gameDto.reverseShowShadow();
		}
		return true;
	}



	@Override
	public boolean KeyFunctionRight() {
		// 暂停
		if(this.gameDto.isGameStart()) {
			this.gameDto.reversePause();
		}
		return true;
	}
	
	
	
	/**
	 * 返回加分数
	 */
	private int removeLines() {
		//获得游戏地图
		boolean[][] gameMap= this.gameDto.getGameMap();
		int removedLineCount=0;
		//扫描游戏地图，查看是否有可以消的行
		for(int y=0; y<gameDto.GAMEZONE_H; y++) {
			//判断是否可以消
			if(isRemovable(y, gameMap)) {
				//若可以，即消行
				this.removeLine(y, gameMap);
				removedLineCount++;
			}
		}
		
		//默认返回0分
		return removedLineCount;
		
	}
	
	
	
	/**
	 * 判断第y行是否可消除
	 * @param y 行数
	 * @return
	 */
	private boolean isRemovable(int rowIdx, boolean[][] gameMap) {
		//单行内扫描每一个方块，看是否填满
		for(int x=0; x<gameDto.GAMEZONE_W; x++) {
			if(!gameMap[x][rowIdx])
			//如果有一个方格为false，则直接跳到下一行
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * 消去当前行
	 * 即在每一列内，从当前行开始，将上一行的方块搬到这一行
	 * @param rowIdx
	 * @param gameMap
	 */
	private void removeLine(int rowIdx, boolean[][] gameMap) {
		for(int x=0; x<gameDto.GAMEZONE_W; x++) {
			for(int y=rowIdx; y>0; y--) {
				gameMap[x][y]= gameMap[x][y-1];
			}
			gameMap[x][0]=false;
		}
	}
	
	
	
	/**
	 * 更新等级、消行数、总分数
	 * @param removedLineCount
	 */
	private void updateDtoExpInformation(int removedLineCount) {
		// 判断是否升级
		if (this.gameDto.getCurrentRemovedLine() % LEVEL_UP_REQUIREMENT + removedLineCount >= LEVEL_UP_REQUIREMENT) {
			this.gameDto.setcurrentLevel(this.gameDto.getcurrentLevel() + 1);
		}
		// 更新当前总消行
		this.gameDto.setCurrentRemovedLine(this.gameDto.getCurrentRemovedLine() + removedLineCount);
		// 更新当前总分数
		this.gameDto.setCurrentPoint(
				this.gameDto.getCurrentPoint() + PLUS_POINT_MAP.get(removedLineCount));
	}



	@Override
	public void startGame() {
		//随机生成下一个方块
		this.gameDto.setNext(random.nextInt(MAX_TYPE-1));
		//随机生成现在方块
		this.gameDto.setGameAct(new GameAct(random.nextInt(MAX_TYPE-1)));
		//把游戏状态设为开始
		this.gameDto.setGameStart(true);
		// DTO初始化
		this.gameDto.gameDtoInit();
		
	}
	
	
	
	@Override
	public void mainAction() {
		this.KeyDown();
	}
	
	
	
	/**
	 * 检查游戏是否失败
	 */
	private boolean isLose() {
		//获得当前游戏方块
		Point[] actPoints= this.gameDto.getGameAct().getActPoints();
		//获得当前游戏地图
		boolean[][] gameMap= this.gameDto.getGameMap();
		for(int i=0; i<actPoints.length; i++) {
			if(gameMap[actPoints[i].x][actPoints[i].y]) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
