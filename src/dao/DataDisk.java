package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataDisk implements Dao{

	private final String filePath;
	
	
	
	public DataDisk(HashMap<String, String> param) {
		this.filePath=param.get("path");
		
	}
	
	
	@Override
	public List<Player> loadData() {
		ObjectInputStream objectInputStream=null;
		List<Player> players=null;
		
		try {
			objectInputStream=new ObjectInputStream(new FileInputStream(filePath));
			players= (List<Player>)objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				objectInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return players;
	}

	@Override
	public void saveData(Player player) {
		//先取出数据
		List<Player> players =this.loadData();
		//追加新记录
		players.add(player);
		//重新写入
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
			objectOutputStream.writeObject(players);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			try {
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
