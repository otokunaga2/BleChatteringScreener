package jp.kobe_u.cs24.service.BleChatteringScreening.logic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatteringScreeningLogic {
	
	
	public int getNumberOfData(){
		int count = 0;
		
		ResultSet rs = MyDBAdopter.getInstance().execute("select count(*) from currents");
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
	
	private static int diff = 0;
	private static int numberOfdata = 0;
//	public static synchronized int start(){
//		int count = 0;
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	public static synchronized int judge(){
	
		
		return 1;
	}
	public int stop(){
		return 1;
	}
	
	private static void judgeWithDateTime(){
		//mysqlのデータが1件更新されていた時に初めて，以下の文を実行する
		System.out.println("hogehoge");
	}
}
