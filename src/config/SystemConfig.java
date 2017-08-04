package config;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

public class SystemConfig implements Serializable{

	private final int minX;
	private final int maxX;
	private final int minY;
	private final int maxY;
	private final int levelUp;
	
	private final List<Boolean> rotatebles;
	private static List<Point[]> actTypeConfig;
	private final Map<Integer, Integer> plusPointMap;
	
	public SystemConfig(Element system) {
		minX= Integer.parseInt(system.attributeValue("minX"));
		maxX= Integer.parseInt(system.attributeValue("maxX"));
		minY= Integer.parseInt(system.attributeValue("minY"));
		maxY= Integer.parseInt(system.attributeValue("maxY"));
		levelUp= Integer.parseInt(system.attributeValue("levelUp"));
		
		List<Element> rects= system.elements("rect");
		rotatebles=new ArrayList<Boolean>(rects.size());
		actTypeConfig= new ArrayList<Point[]>(rects.size());
		
		
		for(Element rect: rects) {
			//添加当前方块是否旋转
			rotatebles.add(Boolean.parseBoolean(rect.attributeValue("rotatable")));
			//获得坐标对象
			List<Element> points= rect.elements("point");
			//创建point数组
			Point[] pointsDest= new Point[points.size()];
			//初始化point对象数组
			for(int i=0; i<pointsDest.length;i++) {
				int x= Integer.parseInt(points.get(i).attributeValue("x"));
				int y= Integer.parseInt(points.get(i).attributeValue("y"));
				pointsDest[i]=new Point(x, y);
			}
			//把point对象数组放入typeconfig中
			actTypeConfig.add(pointsDest);
		}
		
		//获取消行与所加分数的对应
		this.plusPointMap= new HashMap<Integer,Integer>();
		List<Element> plusPointsCfg=system.elements("plusPoint");
		for(Element plusPoint: plusPointsCfg) {
			int rm= Integer.parseInt(plusPoint.attributeValue("rm"));
			int point= Integer.parseInt(plusPoint.attributeValue("point"));
			this.plusPointMap.put(rm, point);
		}
		
	}

	public Map<Integer, Integer> getPlusPointMap() {
		return plusPointMap;
	}

	public List<Boolean> getRotatebles() {
		return rotatebles;
	}

	public int getLevelUp() {
		return levelUp;
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public List<Point[]> getActTypeConfig() {
		return actTypeConfig;
	}


}
