package ui;

import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;
import util.FrameUtil;

public class JFrameGame extends JFrame {
	
	public JFrameGame(JPanelGame jPanelGame) {
		//获得游戏配置
		FrameConfig fCfg= GameConfig.getFrameConfig();
		//设置标题
		this.setTitle(fCfg.getTitle());
		//设置默认关闭属性（程序结束）
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗口大小
		this.setSize(fCfg.getWidth(),fCfg.getHeight());
		//不允许用户改变窗口大小
		this.setResizable(false);
		//窗口居中
		FrameUtil.setFrameCenter(this);
		//设置默认Panel
		this.setContentPane(jPanelGame);
		//默认该窗口为显示
		this.setVisible(true); 
	}
}
