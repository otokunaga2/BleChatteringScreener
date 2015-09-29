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
import jp.kobe_u.cs24.service.BleChatteringScreening.XmlParser;
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
 *  -対象はすごい狭いスコープ(玄関->居間，玄関（一定時間のち）->玄関)などしか想定していない，（もっと複雑にやりたい場合はデータベースに入れたりする必要があるかも
 *    
 * @author tokunaga
 *
 */
public class RouteEstimate extends TimerTask {
	
	HashMap<String,WhenWhere> testPath;
	WhenWhere routeA, routeB;
	private Route currentRoute;
	private HashMap<String, ArrayBlockingQueue<WhenWhere>> workingQueue;
	private String targetUserId;
	public RouteEstimate(HashMap<String,ArrayBlockingQueue<WhenWhere>> holdBlockingQueue){
		currentRoute = new Route(); 
		
		 /*public WhenWhere(String userid, int stationid, String locationName,
					Timestamp lastUpdate) {*/
		 
		 workingQueue = holdBlockingQueue;
		/* testPath.put("tokunaga", routeA);
		 testPath.put("tokunaga", routeB);*/
		 targetUserId = "";
	}
	public RouteEstimate(String userId, HashMap<String,ArrayBlockingQueue<WhenWhere>> holdBlockingQueue){
		this(holdBlockingQueue);
		targetUserId = userId;
	}
	
	
	public ArrayBlockingQueue<WhenWhere> getTargetBlockingQueue(String queryKeyStr){
		ArrayBlockingQueue<WhenWhere> targetQueue = workingQueue.get(queryKeyStr);
		return targetQueue;
	}
	
	public boolean pushDataIntoQueue(){
		String invocationUrl = "http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=";
		invocationUrl = invocationUrl.concat(targetUserId);
//		WhenWhere currentWhenWhere = XmlParser.obtainCurrentDataFromWebAPI(invocationUrl);
		
//		WhenWhere currentWhenWhere;
		return true;
	}
	
	public Timestamp getCurrentTime(){
		Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
		return now;
	}
	public void setSampleMap(){
//		testPath.put(key, value)
	}
	
	
	public Route estimateRouteForUser(){
		
		String routeLabel = "";/*routePathを表す文字列->現時点ではテストを簡単にするために単なるstringにしているが，複数人に返すことを考えるとHashMapなどにする必要があるかも*/
		for(Map.Entry<String, ArrayBlockingQueue<WhenWhere>> e: workingQueue.entrySet()){
			currentRoute.setUserid(e.getKey());
			currentRoute.setTime(Utility.getCurrentTime());
			for(WhenWhere tempHoldData: e.getValue()){
				System.out.println();
				if(e.getValue().size() < 2){
					/*do nothing*/
				}else{
					routeLabel = routeLabel.concat(tempHoldData.getLocationName());
				}
				
			}
			currentRoute.setPathName(mapStringAsPath(routeLabel));
		}
		return currentRoute;
		
	}
	
	/**
	 * 
	 * @return ラベルにヒモ付された経路名（WelcomeHome, GoLiving)などを想定（将来的にどっかのDBに格納したい
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
