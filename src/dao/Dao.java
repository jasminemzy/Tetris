package dao;

import java.util.List;

import dto.Player;

/**
 * 数据持久层借口
 *
 */
public interface Dao {
	
	/**
	 * 读取数据
	 */
	public List<Player> loadData();
	
	/**
	 * 存储数据
	 */
	public void saveData(Player players);
	
	
	
}
