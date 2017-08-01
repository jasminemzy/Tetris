package ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControler;
import control.PlayerControler;
import dto.GameDto;

public class JPanelGame extends JPanel{

	private List<Layer> layers=null;
	
	private GameDto gameDto= null;
	
	private JButton btnStart;
	
	private JButton btnConfig;
	
	private GameControler gameControler= null;
	
	


	/**
	 * 读取配置
	 * 画出游戏界面上各个面板边框
	 */
	public JPanelGame(GameDto gameDto) {
		//获得DTO对象
		this.gameDto=gameDto;
		//设置布局管理器为自由布局
		this.setLayout(null);
		//初始化组件
		initComponent();
		//初始化层
		initLayer();
		//初始化按钮
		initComponent();
	}
	
	
	
	/**
	 * 安装玩家控制器
	 * @param playerControler
	 */
	public void setPlayerControler(PlayerControler playerControler) {
		this.addKeyListener(playerControler);
	}
	/**
	 * 初始化组件
	 */
	private void initComponent() {
		
		//初始化开始按钮
		this.btnStart= new JButton(Img.BTN_START);
		//设置开始按钮位置
		this.btnStart.setBounds(
				GameConfig.getFrameConfig().getButtonConfig().getStartX(), 
				GameConfig.getFrameConfig().getButtonConfig().getStartY(), 
				GameConfig.getFrameConfig().getButtonConfig().getButtonW(),
				GameConfig.getFrameConfig().getButtonConfig().getButtonH());
		this.add(btnStart);
		
		//初始化设置按钮
		this.btnConfig= new JButton(Img.BTN_CONFIG);
		//设置设置按钮位置
		this.btnConfig.setBounds(
				GameConfig.getFrameConfig().getButtonConfig().getUserConfigX(), 
				GameConfig.getFrameConfig().getButtonConfig().getUserConfigY(), 
				GameConfig.getFrameConfig().getButtonConfig().getButtonW(),
				GameConfig.getFrameConfig().getButtonConfig().getButtonH());
		this.btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameControler.showUserConfig();
			}
		});
		
		this.add(btnConfig);
	}
	
	
	
	/**
	 * 初始化层
	 */
	private void initLayer() {
		
		try {
			//获得游戏配置
			FrameConfig fCfg= GameConfig.getFrameConfig();
			//获得图层配置
			List<LayerConfig> layersCfg = fCfg.getLayersConfig();
			//创建游戏层数组
			layers =new ArrayList<Layer>(layersCfg.size());
			//创建所有层对象
			for(LayerConfig layerCfg:layersCfg) {
				//获得类对象
				Class<?> cls= Class.forName(layerCfg.getClassName());
				//获得构造函数
				Constructor cstct=cls.getConstructor(int.class, int.class, int.class, int.class);
				//调用构造函数创建对象
				Layer layer= (Layer)cstct.newInstance(
					layerCfg.getX(), layerCfg.getY(), layerCfg.getW(),layerCfg.getH()
				);
				//设计游戏数据对象
				layer.setGameDto(this.gameDto);
				//把创建的Layer对象放入列表
				layers.add(layer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		//调用基类方法
		super.paintComponent(g);
		//循环刷新游戏画面
		for(int i=0;i<layers.size();layers.get(i++).paint(g));
		//返回焦点
		this.requestFocus();
	}
	
	
	
	public void setGameControler(GameControler gameControler) {
		this.gameControler = gameControler;
	}

	
}
