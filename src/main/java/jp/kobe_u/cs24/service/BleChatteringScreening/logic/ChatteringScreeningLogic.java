package jp.kobe_u.cs24.service.BleChatteringScreening.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import jp.kobe_u.cs24.service.BleChatteringScreening.XmlParser;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

public class ChatteringScreeningLogic extends TimerTask {
	private final int CHATARING_THREASHOLD = 60 * 1000 * 3;/*
													 * チャタリングの調整時間（ひとまず*分に設定しておいて
													 * ，後でDBなどを参照できるようにする）
													 */
	final String countQuery = "select count(*) from currents";
	
	private String callingAPI = "http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=";
	private String userId;
	
	private WhenWhere prevData = null;
	private WhenWhere currentWhenWhere = null;
	private Integer prevDBSize = 0;
	private TimerTask task;
	private ChatteringScreeningLogic self;
	
	public ChatteringScreeningLogic()  {
		prevData = new WhenWhere();
		currentWhenWhere = XmlParser.obtainCurrentDataFromWebAPI(callingAPI);
		userId = "tokunaga";
		callingAPI = callingAPI+userId;
		System.out.println("calling api is:"+callingAPI);
	}
	public ChatteringScreeningLogic(String userid){
		prevData = new WhenWhere();
		currentWhenWhere = XmlParser.obtainCurrentDataFromWebAPI(callingAPI);
		userId = "tokunaga";
		callingAPI = callingAPI+userId;
	}

	public boolean startTimerTask() {
		final Timer timer = new Timer();
		self = new ChatteringScreeningLogic();
		timer.scheduleAtFixedRate(self, 10000, 5000);
		return false;

	}

	public boolean stopTimerTask() {
		return false;

	}

	public void run() {
		WhenWhere currentData = XmlParser
				.obtainCurrentDataFromWebAPI(callingAPI);
		if (prevData != null) {
			if (diffWithPrevDate(prevData, currentData)) {
				currentWhenWhere = currentData;
			}
			prevData = currentData;
		} else {
			prevData = currentData;
		}

	}

	public WhenWhere parsedCurrentAPIData() {

		return null;
	}

	public int getNumberOfData() {
		int count = 0;
		ResultSet rs = MyDBAdopter.getInstance().execute(countQuery);
		// ResultSet rs = mysqlAdopter.execute();
		try {
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;

	}
	public WhenWhere getCurrentWhenWhere(){
		return currentWhenWhere;
	}

	public boolean diffWithPrevDate(WhenWhere prevWhenWhere,
			WhenWhere currentData) {
		/*異なるユーザでも区別して判断できるようにするには*/
		long elapsedTime = currentData.getLastUpdateTimestamp().getTime()
				- prevWhenWhere.getLastUpdateTimestamp().getTime();
		/* 引数の順番を間違えても動くように，絶対値で比較 */
		if (Math.abs(elapsedTime) > CHATARING_THREASHOLD) {

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
