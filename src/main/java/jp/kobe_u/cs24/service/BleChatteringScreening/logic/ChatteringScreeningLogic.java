package jp.kobe_u.cs24.service.BleChatteringScreening.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

import jp.kobe_u.cs24.service.BleChatteringScreening.XmlParser;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

public class ChatteringScreeningLogic {
	private final int CHATARING_THREASHOLD = 60 * 1000 * 5;/*
															 * チャタリングの調整時間（ひとまず*
															 * 分に設定しておいて
															 * ，後でDBなどを参照できるようにする
															 * ）
															 */
	private final int MAX_CHATARING_THREASHOLD = 60 * 60 * 24/*1day*/ * 365 * 10 * 1000; 
	final String countQuery = "select count(*) from currents";
	private String callingAPI = "http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=";
	private String userId;
	private WhenWhere prevData = null;
	private WhenWhere currentWhenWhere = null;
	private Integer prevDBSize = 0;
	private TimerTask task;
	private ChatteringScreeningLogic self;
	private HashMap<String, ArrayBlockingQueue<WhenWhere>> workingQueue;
	private RouteEstimate instanceOfrouteEstimate;
	private static final int numberOfUser = 2;
	private XmlParser xmlParser = null;
	private static StaticWhenWhereQueue staticQueue;
	private ArrayBlockingQueue<WhenWhere> storedQueue;

	private static WhenWhere prevWhenWhere;

	public ChatteringScreeningLogic() {

	}

	public void setStaticPrevData(WhenWhere argWhenWhere) {
		this.prevWhenWhere = argWhenWhere;
	}

	

	public ChatteringScreeningLogic(String userid) {

		ArrayBlockingQueue<WhenWhere> userQueue = new ArrayBlockingQueue<WhenWhere>(
				2);
		/*
		 * ArrayBlockingQueue<WhenWhere> othersQueue = new
		 * ArrayBlockingQueue<WhenWhere
		 * >(2);一つのスレッドで一人に集中する（とりあえずそれでビルドアップしていく方針で
		 */

		prevData = new WhenWhere();
		userId = "tokunaga";
		workingQueue.put(userId, userQueue);
		currentWhenWhere = xmlParser.obtainCurrentDataFromWebAPI(callingAPI);

		callingAPI = callingAPI + userId;
		// instanceOfrouteEstimate = new RouteEstimate(workingQueue);
	}

	public boolean stopTimerTask() {
		return false;

	}

	public void run() {
		/*
		 * Route tempRoute = instanceOfrouteEstimate.estimateRouteForUser();
		 * if("livingentrance".equals(tempRoute.getPathName())){
		 * HttpClient.execGet(); }
		 */
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

	
	
	public WhenWhere getPreviousLocation(String userId) {
		
		currentWhenWhere = xmlParser.obtainCurrentDataFromWebAPI(callingAPI);
		if(currentWhenWhere.isExistance() == false){
			return prevWhenWhere;
		}
		else if (diffWithLocation(prevWhenWhere, currentWhenWhere)/*位置が変化した時にのみデータの更新を実施*/) {
			prevWhenWhere = currentWhenWhere;
			return currentWhenWhere;
		}
		return prevWhenWhere;
	}


	public boolean diffWithLocation(WhenWhere prevWhenWhere, WhenWhere nextWhenWhere){
		if(prevWhenWhere.getLocationName().equals(nextWhenWhere.getLocationName()) && nextWhenWhere.equals("")){
			return true;
		}
		return false;
	}
	
	public boolean diffWithPrevDate(WhenWhere prevWhenWhere,
			WhenWhere currentData) {
		System.out.println("exec diff");
		/* 異なるユーザでも区別して判断できるようにするには */
		long elapsedTime = currentData.getLastUpdateTimestamp().getTime()
				- prevWhenWhere.getLastUpdateTimestamp().getTime();
		System.out.println(elapsedTime);
		/* 引数の順番を間違えても動くように，絶対値で比較 */
		if (Math.abs(elapsedTime) > CHATARING_THREASHOLD && Math.abs(elapsedTime) < MAX_CHATARING_THREASHOLD) {
			System.out.println("exec");
			return true;
		}
		System.out.println("false");
		return true;
	}

	public int getPrevDBSize() {
		return prevDBSize;
	}

	public void setPrevDBSize(int prevDBSize) {
		this.prevDBSize = prevDBSize;
	}
}
