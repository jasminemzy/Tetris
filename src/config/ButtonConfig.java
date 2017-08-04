package config;

import java.io.Serializable;

import org.dom4j.Element;

public class ButtonConfig implements Serializable{
	
	private final int buttonW;
	
	private final int buttonH;
	
	private final int startX;
	
	private final int startY;
	
	private final int userConfigX;
	
	private final int userConfigY;
	
	public ButtonConfig(Element button) {
		
		this.buttonW= Integer.parseInt(button.attributeValue("w"));
		this.buttonH= Integer.parseInt(button.attributeValue("h"));
		
		Element start= button.element("start");
		this.startX= Integer.parseInt(start.attributeValue("x"));
		this.startY= Integer.parseInt(start.attributeValue("y"));

		Element userConfig= button.element("userConfig");
		this.userConfigX= Integer.parseInt(userConfig.attributeValue("x"));
		this.userConfigY= Integer.parseInt(userConfig.attributeValue("y"));
	}

	public int getButtonW() {
		return buttonW;
	}

	public int getButtonH() {
		return buttonH;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getUserConfigX() {
		return userConfigX;
	}

	public int getUserConfigY() {
		return userConfigY;
	}
	
	
}
