package config;

import java.awt.Button;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class FrameConfig {

	 /**
	 * 游戏标题
	 */
	 private final String title;
	
	 /**
	 * 游戏界面高度
	 */
	 private final int width;
	
	 /**
	 * 游戏界面宽度
	 */
	 private final int height;
	
	 /**
	 * 游戏界面页边距
	 */
	 private final int padding;
	
	 /**
	 * 子边框裁切边角大小
	 */
	 private final int border;
	 
	 /**
	 * 方块大小所需移位（<<actSizeRol）
	 */
	private final int actSizeRol;
	
	/**
	 * 游戏失败时方块颜色
	 */
	private final int loseIndex;
	
	/**
	 * 按钮属性
	 */
	private final ButtonConfig buttonConfig;
	 
	 
	/**
	 * 图层配置
	 */
	private List<LayerConfig> layersConfig;
	
	
	
	public FrameConfig(Element frame) {

		this.title=frame.attributeValue("title");
		this.width=Integer.parseInt(frame.attributeValue("width"));
		this.height=Integer.parseInt(frame.attributeValue("height"));
		this.padding=Integer.parseInt(frame.attributeValue("padding"));
		this.border=Integer.parseInt(frame.attributeValue("border"));
		this.actSizeRol= Integer.parseInt(frame.attributeValue("actSizeRol"));
		this.loseIndex= Integer.parseInt(frame.attributeValue("loseIndex"));
		
		List<Element>layers= frame.elements("layer");
		this.layersConfig = new ArrayList<LayerConfig>();

		for(Element layer: layers) {
			LayerConfig lc= new LayerConfig(
				layer.attributeValue("className"),
				Integer.parseInt(layer.attributeValue("x")),
				Integer.parseInt(layer.attributeValue("y")),
				Integer.parseInt(layer.attributeValue("w")),
				Integer.parseInt(layer.attributeValue("h"))
			);
			layersConfig.add(lc);
		}
		
		//初始化按钮属性
		buttonConfig= new ButtonConfig(frame.element("button"));
		
	}



	public ButtonConfig getButtonConfig() {
		return buttonConfig;
	}



	public int getLoseIndex() {
		return loseIndex;
	}



	public int getActSizeRol() {
		return actSizeRol;
	}



	public String getTitle() {
		return title;
	}



	public int getWidth() {
		return width;
	}



	public int getHeight() {
		return height;
	}



	public int getPadding() {
		return padding;
	}



	public int getBorder() {
		return border;
	}



	public List<LayerConfig> getLayersConfig() {
		return layersConfig;
	}
	
	
}
