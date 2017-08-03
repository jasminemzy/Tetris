package ui.cfg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import control.GameControler;
import util.FrameUtil;

public class FrameConfig extends JFrame{
	
	private JButton btnConfirm= new JButton("确定");
	
	private JButton btnCancel= new JButton("取消");
	
	private JButton btnApply= new JButton("应用");
	
	private TextControler[] keyTexts= 	 new TextControler[8];
	
	private JLabel errorMsg=new JLabel();
	
	private final static Image IMG_PSP= new ImageIcon("data/psp.jpg").getImage();
	
	private final static String[] METHOD_NAMES= {
			"KeyRight", "KeyUp", "KeyLeft", "KeyDown",
			"KeyFunctionLeft", "KeyFunctionUp", "KeyFunctionRight",  "KeyFunctionDown"
	};
	
	private final static String PATH= "data/control.dat";
	
	private GameControler gameControler;
	
	
	
	public FrameConfig(GameControler gameControler) {
		//获得游戏控制器
		this.gameControler=gameControler;
		//设置布局管理器为边界布局
		this.setLayout(new BorderLayout());
		//标题
		this.setTitle("游戏设置");
		//初始化按键输入框
		this.initKeyTexts();
		//添加主面板
		this.add(createMainPanel(), BorderLayout.CENTER);
		//添加按钮面板
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		//设置不能改变大小
		this.setResizable(false);
		//设置窗口大小
		this.setSize(600, 360);
		//居中
		FrameUtil.setFrameCenter(this);
	}
	
	
	
	/**
	 * 初始化按键输入框
	 */
	private void initKeyTexts() {
		int x=8;
		int y=40;
		int w=40;
		int h=20;
		for(int i=0; i<4; i++) {
			keyTexts[i]= new TextControler(x, y+32*i, w, h, METHOD_NAMES[i]);
		}
		x=530;
		for(int i=4; i<8; i++) {
			keyTexts[i]= new TextControler(x, y+32*(i-4), w, h, METHOD_NAMES[i]);
		}
		try {
			ObjectInputStream ois= new ObjectInputStream(new FileInputStream(PATH));
			HashMap<Integer, String> cfgSet= (HashMap<Integer, String>) ois.readObject();
			ois.close();
			Set<Entry<Integer, String>> entrySet= cfgSet.entrySet();
			for(Entry<Integer, String> e: entrySet) {
				for(TextControler tc: keyTexts) {
					if(tc.getMethodName().equals(e.getValue())){
						tc.setKeyCode(e.getKey());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}



	/**
	 * 创建按钮面板
	 * @return
	 */
	private JPanel createButtonPanel() {
		
		//创建按钮面板，流式布局
		JPanel jPanel= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		//给确定按钮增加事件监听
		this.btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(writeConfig()) {
					setVisible(false);
					gameControler.setOver();
				}
			}
		});
		//给取消按钮增加事件监听
		this.btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControler.setOver();
			}
		});
		//给应用按钮增加事件监听
		this.btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeConfig();
			}
		});
		
		this.errorMsg.setForeground(Color.BLACK);
		this.errorMsg.setFont(new Font("黑体", Font.BOLD, 14));
		jPanel.add(errorMsg);
		//显示确定按钮
		jPanel.add(this.btnConfirm);
		//显示取消按钮
		jPanel.add(this.btnCancel);
		//显示应用按钮
		jPanel.add(this.btnApply);
		
		//讲创建好的按钮面板返回
		return jPanel;
	}
	
	
	
	/**
	 * 创建主面板（选卡项面板）
	 * @return
	 */
	private JTabbedPane createMainPanel() {
		
		JTabbedPane jTabbedPane= new JTabbedPane();
		jTabbedPane.addTab("控制设置", this.createControlPanel());
		jTabbedPane.addTab("皮肤设置", new JLabel("皮肤"));
		
		return jTabbedPane;
	}
	
	
	
	/**
	 * 玩家控制选卡项设置面板
	 * @return
	 */
	private JPanel createControlPanel() {
		JPanel jPanel= new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		//设置布局管理器
		jPanel.setLayout(null);

		for(int i=0; i<keyTexts.length; i++) {
			jPanel.add(keyTexts[i]);
		}
		
		return jPanel;
		
	}
	
	
	
	
	/**
	 * 写入游戏配置
	 */
	private boolean writeConfig() {
		HashMap<Integer, String> keySet= new HashMap<Integer, String>();
		for(int i=0; i<this.keyTexts.length; i++) {
			int keyCode= this.keyTexts[i].getKeyCode();
			if(keyCode==0) {
				this.errorMsg.setText("错误按键");
				return false;
			}
			keySet.put(keyCode, this.keyTexts[i].getMethodName());
		}
		if(keySet.size()!=8) {
			this.errorMsg.setText("重复按键");
			return false;
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.errorMsg.setText(e.getMessage());
			return false;
		} 
		this.errorMsg.setText(null);
		return true;
	}
	
	
	
	
}
