package config;

public class LayerConfig {
	private String className;
	private int x;
	private int y;
	private int w;
	private int h;
	
	
	
	/**
	 * 构造器
	 * 
	 * @param className 图层名
	 * @param x	图层左上角点x坐标
	 * @param y	图层左上角点y坐标
	 * @param w	图层宽度
	 * @param h 图层高度
	 */
	public LayerConfig(String className, int x, int y, int w, int h) {
		super();
		this.className = className;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	
	
	/**
	 * GET方法
	 */
	public String getClassName() {
		return className;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}
	
	
	
}
