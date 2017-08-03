package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Dao;
import dao.DataBase;
import service.GameService;
import service.GameTetris;
import ui.JPanelGame;
import ui.cfg.FrameConfig;
import ui.cfg.TextControler;

/**
 * 接受玩家键盘事件
 * 控制画面
 * 控制游戏逻辑
 * @author jasminemzy
 *
 */
public class GameControler {

	
	/**
	 * 数据访问接口A
	 */
	private Dao daoA;
	
	/**
	 * 数据访问接口B
	 */
	private Dao daoB;
	
	
	/**
	 * 游戏界面层
	 */
	private JPanelGame jPanelGame;
	
	/**
	 * 游戏用户配置窗口
	 */
	private FrameConfig frameConfig;
	
	/**
	 * 游戏逻辑层
	 */
	private GameService gameService;
	
	/**
	 * 游戏行为控制
	 */
	private Map<Integer, Method> actionMap;
	
	
	
	public GameControler(JPanelGame jPanelGame, GameTetris gameService) {
		this.jPanelGame=jPanelGame;
		this.gameService=gameService;
		//从数据接口A获得数据库记录
		//获得配置
		DataInterfaceConfig cfgDataA=GameConfig.getDataConfig().getDataA() ;

		//获得类对象
		daoA= new DataBase();
		//设置数据库记录到游戏
		this.gameService.setDbRecord(daoA.loadData());
		//从数据接口B获得本地磁盘记录
		daoB= createDataObject(GameConfig.getDataConfig().getDataB());
		//设置本地磁盘记录到游戏
		this.gameService.setDiskRecord(daoB.loadData());
		//初始化游戏行为
		this.setControlConfig();
		this.frameConfig= new FrameConfig(this);
		

	}
	
	
	
	/**
	 * 读取用户控制设置
	 */
	private void setControlConfig() {
		//创建键盘码与方法名的映射数组
		this.actionMap= new HashMap<Integer,Method>();
		try {
			ObjectInputStream ois= new ObjectInputStream(new FileInputStream("data/control.dat"));
			HashMap<Integer, String> cfgSet= (HashMap<Integer, String>) ois.readObject();
			Set<Entry<Integer, String>> entrySet= cfgSet.entrySet();
			for(Entry<Integer, String> e: entrySet) {
				actionMap.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * 创建数据对象
	 * @param cfg
	 * @return
	 */
	private Dao createDataObject(DataInterfaceConfig cfg) {

		try {
			//获得类对象
			Class<?> cls= Class.forName(cfg.getClassName());
			//获得构造器
			Constructor<?> constructor= cls.getConstructor(HashMap.class);
			//用构造器创建对象
			return (Dao) (constructor.newInstance(cfg.getParam()));
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	/**
	 * 根据玩家控制决定行为
	 * @param keyCode
	 */
	public void actionByKeyCode(int keyCode) {
		try {
			//只有按键有对应方法时才会反射方法
			if(this.actionMap.containsKey(keyCode)) {
				//根据按键调用方法
				this.actionMap.get(keyCode).invoke(this.gameService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.jPanelGame.repaint();
	}



	/**
	 * 显示玩家配置窗口
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
		this.frameConfig.requestFocus();
	}



	/**
	 * 子窗口关闭事件
	 */
	public void setOver() {
		this.jPanelGame.repaint();
		this.setControlConfig();
	}



	/**
	 * 开始按钮事件
	 */
	public void startGame() {
		this.jPanelGame.buttonSwitch(false);
		this.gameService.startMainThread();
		this.jPanelGame.repaint();
		
	}
	
	
	
}
