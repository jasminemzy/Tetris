package entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import config.GameConfig;

public class GameAct {
	
	/**
	 * 方块数组
	 */
	private Point[] actPoints=null;
	
	private int actType;
	
	private final static int MIN_X= GameConfig.getSystemConfig().getMinX();
	private final static int MAX_X= GameConfig.getSystemConfig().getMaxX();
	private final static int MIN_Y= GameConfig.getSystemConfig().getMinY();
	private final static int MAX_Y= GameConfig.getSystemConfig().getMaxY();
	
	private final static List<Boolean> ROTATABLES= GameConfig.getSystemConfig().getRotatebles();
	private final static List<Point[]> ACT_TYPE_CONFIG= GameConfig.getSystemConfig().getActTypeConfig();
	
//	static {
//		ACT_TYPE_CONFIG= new ArrayList<Point[]>(7);
//		ACT_TYPE_CONFIG.add(new Point[] {new Point(4,0), new Point(3,0), new Point(5,0), new 	Point(6,0)});
//		ACT_TYPE_CONFIG.add(new Point[] {new Point(4,0), new Point(3,0), new Point(5,0), new 	Point(4,1)});
//		ACT_TYPE_CONFIG.add(new Point[] {new Point(4,0), new Point(3,0), new Point(5,0), new 	Point(3,1)});
//		ACT_TYPE_CONFIG.add(new Point[] {new Point(4,0), new Point(5,0), new Point(3,1), new 	Point(4,1)});
//		ACT_TYPE_CONFIG.add(new Point[] {new Point(4,0), new Point(5,0), new Point(4,1), new 	Point(5,1)});
//		ACT_TYPE_CONFIG.add(new Point[] {new Point(4,0), new Point(3,0), new Point(5,0), new 	Point(5,1)});
//		ACT_TYPE_CONFIG.add(new Point[] {new Point(4,0), new Point(3,0), new Point(4,1), new 	Point(5,1)});
//	}
	
	
	
	public GameAct(int actType) {
		this.init(actType);
	}
	
	
	
	public void init(int actType) {
		
		this.actType=actType;
		Point[] ACT_MODEL= ACT_TYPE_CONFIG.get(actType);
		actPoints= new Point[ACT_MODEL.length];
		for(int i=0; i<actPoints.length; i++) {
			actPoints[i]= new Point(ACT_MODEL[i].x, ACT_MODEL[i].y);
		}
	}


	
	
	/**
	 * 方块移动方法
	 * @param moveX x轴偏移量
	 * @param moveY y轴偏移量
	 */
	public boolean moveAct(int moveX, int moveY, boolean[][] gameMap) {
		
		//若任意方块不满足条件，不做处理直接返回
		for(int i=0; i<this.actPoints.length; i++) {
			int newX= this.actPoints[i].x+ moveX;
			int newY= this.actPoints[i].y+ moveY;
			if(this.isOverZone(newX, newY, gameMap)) {
				return false;
			}
		}
		
		//
		for(int i=0; i<this.actPoints.length; i++) {
			this.actPoints[i].x+= moveX;
			this.actPoints[i].y+= moveY;
		}
		return true;
	}
	
	
	
	/**
	 * 方块旋转
	 * 顺时针：
	 * A.x= 0.y+ 0.x- B.y
	 * A.y= 0.y- 0.x+ B.x
	 */
	public void rotateAct(boolean[][] gameMap) {
		//正方形方块不允许转动
		
		if(!ROTATABLES.get(this.actType))
			return;
		
		for(int i=1; i<actPoints.length; i++) {
			int newX= actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY= actPoints[0].y - actPoints[0].x + actPoints[i].x;
			//TODO p)判断是否可以旋转
			if(this.isOverZone(newX, newY, gameMap)){
				return;
			}
		}
		for(int i=1; i<actPoints.length; i++) {
			int newX= actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY= actPoints[0].y - actPoints[0].x + actPoints[i].x;
			actPoints[i].x= newX;
			actPoints[i].y= newY;
		}
	}
	
	
	
	/**
	 * 判断是否超出边界 
	 * @param x	点横坐标
	 * @param y 点纵坐标
	 * @return 
	 */
	private boolean isOverZone(int x, int y, boolean[][] gameMap) {
		return x<MIN_X || x>MAX_X || y<MIN_Y || y>MAX_Y || gameMap[x][y];
	}
	
	
	
	public int getActType() {
		return actType;
	}



	public Point[] getActPoints() {
		return actPoints;
	}
	
	
	
}
