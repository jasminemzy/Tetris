package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerControler extends KeyAdapter{

	private GameControler gameControler;
	
	public PlayerControler(GameControler gameControler) {
		this.gameControler=gameControler;
	}
	
	
	
	/**
	 * 键盘按下事件
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		this.gameControler.actionByKeyCode(e.getKeyCode());
		
	}



	
}
