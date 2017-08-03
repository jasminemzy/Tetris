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
<<<<<<< HEAD
		//TODO p)这样的枚举写法不好
		
		this.gameControler.actionByKeyCode(e.getKeyCode());
		
//		switch (e.getKeyCode()) {
//		case KeyEvent.VK_UP:
//			this.gameControler.keyUp();
//			break;
//		case KeyEvent.VK_DOWN:
//			this.gameControler.keyDown();
//			break;
//		case KeyEvent.VK_LEFT:
//			this.gameControler.keyLeft();
//			break;
//		case KeyEvent.VK_RIGHT:
//			this.gameControler.keyRight();
//			break;
//		//TODO p)测试专用
//		case KeyEvent.VK_W:
//			this.gameControler.keyTest();
//			break;
//		default:
//			break;
//		}
=======
		
		this.gameControler.actionByKeyCode(e.getKeyCode());
		
>>>>>>> try using cmd
	}



	
}
