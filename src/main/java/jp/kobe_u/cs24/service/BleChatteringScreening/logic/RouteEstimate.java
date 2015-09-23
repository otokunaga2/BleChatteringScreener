package jp.kobe_u.cs24.service.BleChatteringScreening.logic;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.glassfish.jersey.server.model.Routed;

import jp.kobe_u.cs24.service.BleChatteringScreening.Utility;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.Route;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

/**
 *** 説明
 *  このクラスは，経路の判定を行うためのクラス
 *   -ユーザごとにそれぞれ判定できるようにHashMapにデータをもたせている（将来的にはRedisなどのkey valueデータベースに突っ込みたい）
 *   -判定ロジックは一旦，ゆるくハードコード，必要あれば，DBに格納するように変更する
 *  
 * 
 *** メモ
 *  -対象はすごい狭いスコープ(玄関->居間，玄関（一定時間のち）->玄関)などしか想定していない，（もっと複雑にやりたい場合はグラフデータベースに入れたりする必要があるかも
 *    
 * @author tokunaga
 *
 */
public class RouteEstimate extends TimerTask {
	
	HashMap<String,WhenWhere> testPath;
	WhenWhere routeA, routeB;
	private Route currentRoute;
	private HashMap<String, ArrayBlockingQueue<WhenWhere>> workingQueue;
	public RouteEstimate(HashMap<String,ArrayBlockingQueue<WhenWhere>> holdBlockingQueue){
		currentRoute = new Route(); 
		testPath = new HashMap<String,WhenWhere>();
		 /*public WhenWhere(String userid, int stationid, String locationName,
					Timestamp lastUpdate) {*/
		 
		 workingQueue = holdBlockingQueue;
		 testPath.put("tokunaga", routeA);
		 testPath.put("tokunaga", routeB);
		 
	}
	
	public Timestamp getCurrentTime(){
		Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
		return now;
	}
	public void setSampleMap(){
//		testPath.put(key, value)
	}
	
	
	public Route estimateRouteForUser(){
		System.out.println();
		String routeLabel = "";/*routePathを表す文字列->現時点ではテストを簡単にするために単なるstringにしているが，複数人に返すことを考えるとHashMapなどにする必要があるかも*/
		for(Map.Entry<String, ArrayBlockingQueue<WhenWhere>> e: workingQueue.entrySet()){
			currentRoute.setUserid(e.getKey());
			currentRoute.setTime(Utility.getCurrentTime());
			for(WhenWhere tempHoldData: e.getValue()){
				System.out.println();
				routeLabel = routeLabel.concat(tempHoldData.getLocationName());
			}
			currentRoute.setPathName(mapStringAsPath(routeLabel));
		}
		
		return currentRoute;
		
	}
	
	/**
	 * 
	 * @return ラベルにヒモ付された経路名（WelcomeHome, GoLiving, G)などを想定（どっかのDBに格納したい
	 */
	public String mapStringAsPath(final String concatLabel){
		String returnLabel = "";
		switch(concatLabel){
			case "entranceentrance": 
				returnLabel = "WelcomeHome";
				break;
			case "entranceliving":
				returnLabel = "GoLiving";
				break;
			case "livingentrance":
				returnLabel = "Outgo";
				break;
			default:
			break;
		}
		
		return returnLabel;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
