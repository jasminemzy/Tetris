package service;

import java.awt.Point;
import java.util.List;
import java.util.Random;

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
	private static final int MAX_TYPE= 6;
	
	public GameTetris(GameDto gameDto) {
		this.gameDto=gameDto;
		GameAct gameAct= new GameAct(random.nextInt(MAX_TYPE));
		gameDto.setGameAct(gameAct);
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
		// 判断是否可以消行
		// 消行操作
		// 算分操作
		// 判断是否升级
		// 升级
		
		
		//在顶部创建新的下一个方块
		this.gameDto.getGameAct().init(this.gameDto.getNext());
		//随机生成再下一个方块
		this.gameDto.setNext(random.nextInt(MAX_TYPE));
		
		
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



}
