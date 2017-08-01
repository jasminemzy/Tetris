package util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameUtil {

	/**
	 * 窗口居中
	 * @param jFrame
	 */
	public static void setFrameCenter(JFrame jFrame) {
		//窗口居中
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Dimension screen= toolkit.getScreenSize();
		int x=(screen.width-jFrame.getWidth())/2;
		int y=(screen.height-jFrame.getHeight())/2;
		jFrame.setLocation(x, y);
		
	}
	
	
}
