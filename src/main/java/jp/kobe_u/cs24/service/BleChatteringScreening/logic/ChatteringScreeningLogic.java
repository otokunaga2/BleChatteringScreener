package jp.kobe_u.cs24.service.BleChatteringScreening.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.TimerTask;

import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

public class ChatteringScreeningLogic extends TimerTask{
	private static final int WAITTIME = 5000;
	private final int CHATARING_THREASHOLD = 6000*3;/*チャタリングの調整時間（ひとまず*分に設定しておいて，後でDBなどを参照できるようにする）*/
	final String countQuery = "select count(*) from currents";
	private WhenWhere  prevDate = null;
	private Integer prevDBSize = 0; 
	public ChatteringScreeningLogic(){
		prevDate = new WhenWhere();
	}
	
	public void run(){
		synchronized (prevDate) {
			if(prevDate != null){
			}
		}
	}
	
	
	public WhenWhere parsedCurrentAPIData(){
		
		return null;
	}
	
	public int getNumberOfData(){
		int count = 0;
		ResultSet rs = MyDBAdopter.getInstance().execute(countQuery);
		//ResultSet rs = mysqlAdopter.execute();
		try {
			while(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}
	
	public boolean diffWithPrevDate(WhenWhere prevWhenWhere, WhenWhere currentData){
		int elapsedTime = currentData.getLastUpdateTimestamp().compareTo(prevWhenWhere.getLastUpdateTimestamp()); 
		
		if (elapsedTime > CHATARING_THREASHOLD){
			/*一定時間経過しているので，データの書き換えを認める*/
			return true;
		}
		return false;
	}
	public int getPrevDBSize() {
		return prevDBSize;
	}

	public void setPrevDBSize(int prevDBSize) {
		this.prevDBSize = prevDBSize;
	}
}
