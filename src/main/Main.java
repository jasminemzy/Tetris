package main;

import control.GameControler;
import control.PlayerControler;
import dto.GameDto;
import service.GameTetris;
import ui.JFrameGame;
import ui.JPanelGame;

public class Main {
	
	public static void main(String[] args) {
		//创建游戏数据源
		GameDto gameDto= new GameDto();
		//创建游戏面板
		JPanelGame jPanelGame= new JPanelGame(gameDto);
		//创建游戏逻辑块（连接游戏数据源）
		GameTetris gameService= new GameTetris(gameDto);
		//创建游戏控制器（连接游戏面板与游戏逻辑块）
		GameControler gameControler= new GameControler(jPanelGame, gameService);
		//将游戏控制器对象交给panel
		jPanelGame.setGameControler(gameControler);
		//创建玩家控制器（连接游戏控制器）
		PlayerControler playerControler= new PlayerControler(gameControler);
		//安装玩家控制器
		jPanelGame.setPlayerControler(playerControler);
		//创建游戏窗口，安装游戏面板
		JFrameGame jFrameGame= new JFrameGame(jPanelGame);
	}
}
