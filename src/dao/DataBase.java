package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Player;

public class DataBase implements Dao{
	
	private String DRIVER;
	
	private String DB_URI;
	
	private String DB_USER;
	
	private String DB_PWD;
	
	
	@Override
	public List<Player> loadData() {
		List<Player> players= new ArrayList<Player>();
		players.add(new Player("麻将", 1001));
		players.add(new Player("麻酱酱", 3000));
		players.add(new Player("小麻将", 2048));
		players.add(new Player("笨蛋小麻将", 640));
		players.add(new Player("天才小麻将", 4000));
		return players;
	}

	@Override
	public void saveData(Player players) {
		
	}

}
