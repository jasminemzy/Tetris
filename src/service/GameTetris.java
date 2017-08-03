package service;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import dto.Player;
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
	public void KeyUp() {
		this.gameDto.getGameAct().rotateAct(this.gameDto.getGameMap());			
	}



	/**
	 * 方块操作（下）
	 */
	@Override
	public void KeyDown() {
		
		//执行向下降落操作，如果可以移动就结束
		if(this.gameDto.getGameAct().moveAct(0, 1,this.gameDto.getGameMap())) {
			return;
		}
		//如果不可继续向下移动，执行下列额外操作
		//获得游戏地图对象
		boolean[][] gameMap= this.gameDto.getGameMap();
		//获得方块对象
		Point[] gameAct=this.gameDto.getGameAct().getActPoints();
		//将方块堆积到地图数组
		for(int i=0; i<gameAct.length; i++) {
			gameMap[gameAct[i].x][gameAct[i].y]= true;
		}

		// 执行消行，并返回此次共消了多少行
		int removedLineCount= this.removeLines();
		//更新等级、总消行数、总分数
		if(removedLineCount>0) {
			this.updateDtoExpInformation(removedLineCount);
		}
		
		//在顶部创建新的下一个方块
		this.gameDto.getGameAct().init(this.gameDto.getNext());
		//随机生成再下一个方块
		this.gameDto.setNext(random.nextInt(MAX_TYPE-1));
		

	}



	/**
	 * 方块操作（左）
	 */
	@Override
	public void KeyLeft() {
		this.gameDto.getGameAct().moveAct(-1, 0,this.gameDto.getGameMap());
	}



	/**
	 * 方块操作（右）
	 */
	@Override
	public void KeyRight() {
		this.gameDto.getGameAct().moveAct(1, 0,this.gameDto.getGameMap());
	}





	



	@Override
	public void KeyFunctionUp() {
		// TODO p)测试专用
		this.gameDto.setCurrentPoint(this.gameDto.getCurrentPoint() + 10);
		this.gameDto.setCurrentRemovedLine(this.gameDto.getCurrentRemovedLine() + 1);
		if (this.gameDto.getCurrentRemovedLine() % 20 == 0)
			this.gameDto.setcurrentLevel(this.gameDto.getcurrentLevel() + 1);
	}




	@Override
	public void KeyFunctionDown() {
		System.out.println("大叉按下");
	}




	@Override
	public void KeyFunctionLeft() {
		System.out.println("方块按下");		
	}




	@Override
	public void KeyFunctionRight() {
		System.out.println("圆圈按下");
	}
	
	
	
	public void setDbRecord(List<Player> players) {
		this.gameDto.setDbRecord(players);
	}
	
	
	
	public void setDiskRecord(List<Player> players) {
		this.gameDto.setDiskRecord(players);
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
	public void startMainThread() {
		//随机生成下一个方块
		this.gameDto.setNext(random.nextInt(MAX_TYPE-1));
		//随机生成现在方块
		this.gameDto.setGameAct(new GameAct(random.nextInt(MAX_TYPE-1)));
		//把游戏状态设为开始
		this.gameDto.setGameStart(true);
		
		
		
	}
	
	
	
	
	
}
