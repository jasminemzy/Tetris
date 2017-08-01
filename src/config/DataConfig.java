package config;

import org.dom4j.Element;

public class DataConfig {

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
