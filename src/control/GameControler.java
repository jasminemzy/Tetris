package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Dao;
import dao.DataBase;
import dto.GameDto;
import dto.Player;
import service.GameService;
import service.GameTetris;
import ui.JFrameGame;
import ui.JPanelGame;
import ui.cfgWindow.FrameConfig;
import ui.cfgWindow.FrameSavePoint;

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
	 * 游戏用户配置弹窗
	 */
	private FrameConfig frameConfig;
	
	/**
	 * 存储分数弹窗
	 */
	private FrameSavePoint frameSavePoint;
	
	/**
	 * 按键控制器
	 */
	private PlayerControler playerControler;
	
	/**
	 * 游戏逻辑层
	 */
	private GameService gameService;
	
	/**
	 * 游戏行为控制
	 */
	private Map<Integer, Method> actionMap;
	
	/**
	 * 游戏线程
	 */
	private Thread gameThread= null;
	
	
	private GameDto gameDto= null;
	
	
	
	public GameControler() {
		
		// 创建游戏数据源
		this.gameDto = new GameDto();
		// 创建游戏逻辑块（连接游戏数据源）
		this.gameService = new GameTetris(this.gameDto);
		// 获得类对象
		daoA = new DataBase();
		// 设置数据库记录到游戏
		this.gameDto.setDbRecord(daoA.loadData());
		// 从数据接口B获得本地磁盘记录
		daoB = createDataObject(GameConfig.getDataConfig().getDataB());
		// 设置本地磁盘记录到游戏
		this.gameDto.setDiskRecord(daoB.loadData());
		// 创建游戏面板
		this.jPanelGame = new JPanelGame(this, this.gameDto);
		// 读取用户控制设置
		this.setControlConfig();
		// 初始化用户配置弹窗
		this.frameConfig= new FrameConfig(this);
		// 初始化存储分数弹窗
		this.frameSavePoint= new FrameSavePoint(this);
		//创建游戏主窗口，安装游戏面板
		new JFrameGame(this.jPanelGame);
		

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
		this.jPanelGame.setFocusable(false);
		this.frameConfig.setVisible(true);
		this.frameConfig.requestFocus();
		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while(!frameConfig.hasFocus()) {
//					System.out.println(frameConfig.requestFocusInWindow());
//				}
//			}
//		}).start();

//		System.out.println(this.frameConfig.requestFocusInWindow());
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
		// 面板按钮设置为不可点击
		this.jPanelGame.buttonSwitch(false);
		//关闭弹窗
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		// 游戏数据初始化
		this.gameService.startGame();
		//创建线程对象
		this.gameThread= new MainThread();
		//启动线程
		this.gameThread.start();
		//刷新画面
		this.jPanelGame.repaint();
		this.jPanelGame.setJPanelFocused();
	}
	
	
	
	/**
	 * 失败后的处理
	 */
	public void afterLose() {
		//显示保存得分窗口
		this.jPanelGame.setFocusable(false);
		this.frameSavePoint.requestFocus();
		this.frameSavePoint.setVisible(true);
		// 显示当前分数
		this.frameSavePoint.showSaveWindow(this.gameDto.getCurrentPoint());
		// 使按钮可以点击
		this.jPanelGame.buttonSwitch(true);
		
	}
	
	
	
	private class MainThread extends Thread {

		@Override
		public void run() {
			// 刷新画面
			jPanelGame.repaint();
			// 线程主循环
			while (gameDto.isGameStart()) {
				try {
					// 线程睡眠
					Thread.sleep(gameDto.getSleepTime());
					// 如果暂停，不执行主行为
					if(gameDto.isPause()) {
						continue;
					}
					// 方块下落
					gameService.mainAction();
					// 刷新画面
					jPanelGame.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}
	}



	/**
	 * 保存分数
	 * @param name
	 */
	public void savePoint(String name) {
		Player player= new Player(name, this.gameDto.getCurrentPoint());
		//保存记录到本地磁盘
		this.daoB.saveData(player);
		//设计磁盘记录到游戏
		this.gameDto.setDiskRecord(daoB.loadData());
		//刷新画面
		this.jPanelGame.repaint();
	}
	
	
	
	public void setJPanelFocused() {
		this.jPanelGame.setFocusable(true);
		this.jPanelGame.requestFocus();
	}
	
	
	
	/**
	 * 刷新画面
	 */
	public void repaint() {
		this.jPanelGame.repaint();
	}
}
