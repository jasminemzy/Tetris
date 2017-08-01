package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Player;

public class DataTest implements Dao{

	@Override
	public List<Player> loadData() {
		List<Player> players= new ArrayList<Player>();
		players.add(new Player("麻将", 100));
		players.add(new Player("麻将", 300));
		players.add(new Player("小麻将", 2000));
		players.add(new Player("麻将", 3050));
//		players.add(new Player("麻将", 400));
		return players;
	}

	@Override
	public void saveData(Player players) {
		System.out.println();
	}

}
