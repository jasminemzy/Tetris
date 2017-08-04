package config;

import java.io.Serializable;

import org.dom4j.Element;

public class DataConfig implements Serializable{

	private final DataInterfaceConfig dataA;
	
	private final DataInterfaceConfig dataB;
	
	private final int maxRow;
	
	
	public DataConfig(Element data) {
		dataA= new DataInterfaceConfig(data.element("dataA"));
		dataB= new DataInterfaceConfig(data.element("dataB"));
		maxRow= Integer.parseInt(data.attributeValue("maxRow"));
		
	}
	
	
	
	public DataInterfaceConfig getDataA() {
		return dataA;
	}

	public int getMaxRow() {
		return maxRow;
	}



	public DataInterfaceConfig getDataB() {
		return dataB;
	}
	
}
