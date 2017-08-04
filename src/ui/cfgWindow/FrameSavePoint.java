package ui.cfgWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.GameControler;
import util.FrameUtil;

public class FrameSavePoint extends JFrame{

	private JLabel lbPoint= null;
	
	private JTextField txName= null;
	
	private JButton btnOK= null;
	
	private JLabel errorMsg=null;
	
	private GameControler gameControler= null;
	
	
	
	public FrameSavePoint(GameControler gameControler) {
		this.gameControler= gameControler;
		this.setTitle("保存记录");
		this.setSize(256,128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.createComponents();
		this.createAction();
	}
	
	
	/* 
	 * 显示窗口
	 */
	public void showSaveWindow(int point) {
		this.lbPoint.setText("您的得分："+ point);
		this.lbPoint.setVisible(true);
	}
	
	
	
	/**
	 * 创建时间监听
	 */
	private void createAction() {
		this.btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 还可以加入其它文字验证
				String name= txName.getText();
				if(name.length()>16 || name==null || "".equals(name)) {
					errorMsg.setText("   请输入16位以内名字");
					errorMsg.repaint();
				}else {
					gameControler.savePoint(name);
					setVisible(false);
					gameControler.setJPanelFocused();
				}
			}
		});
	}



	/**
	 * 初始化控件
	 */
	private void createComponents() {
		
		// 北部面板
		JPanel north= new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.lbPoint= new JLabel();
		north.add(this.lbPoint);
		// 创建错误信息提示
		this.errorMsg= new JLabel();
		this.errorMsg.setForeground(Color.RED);
		north.add(this.errorMsg);
		this.add(north, BorderLayout.NORTH);
		
		//中部面板
		JPanel center= new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.txName= new JTextField(10);
		// TODO 设置最大长度
		center.add(new Label("您的名字："));
		center.add(this.txName);
		this.add(center, BorderLayout.CENTER);
		
		
		// 创建南部面板（流式布局）
		JPanel south= new JPanel(new FlowLayout(FlowLayout.CENTER));
		// 创建确定按钮
		this.btnOK= new JButton("确定");
		// 将按钮添加到南部面板
		south.add(btnOK);
		// 将南部面板添加到主面板
		this.add(south, BorderLayout.SOUTH);
	}
	
	
	
}
