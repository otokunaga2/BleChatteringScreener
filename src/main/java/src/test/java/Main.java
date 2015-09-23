package src.test.java;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import jp.kobe_u.cs24.service.BleChatteringScreening.logic.ChatteringScreeningLogic;
import jp.kobe_u.cs24.service.BleChatteringScreening.logic.RouteEstimate;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

public class Main {
	
	
	private static ArrayBlockingQueue<WhenWhere> holdBlockingQueue;
	private static HashMap<String, ArrayBlockingQueue<WhenWhere>> monitorMap;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testRouteEstimate();
	}
	public static void testChatteringLogic(){
		ChatteringScreeningLogic testLogic = new ChatteringScreeningLogic();
		testLogic.startTimerTask();
	}
	public static void testRouteEstimate(){
		ArrayBlockingQueue<WhenWhere> holdBlockingQueue = new ArrayBlockingQueue<>(2);
		
		WhenWhere routeB = new WhenWhere("tokunaga",1,"nearLiving",getCurrentTime());
		WhenWhere routeA = new WhenWhere("tokunaga",1,"nearEntrance",getCurrentTime());
		HashMap<String, ArrayBlockingQueue<WhenWhere>>  monitorMap = new HashMap<String, ArrayBlockingQueue<WhenWhere>>();
		try {
			holdBlockingQueue.put(routeA);
			/*put is waiting if the queue is full*/
			holdBlockingQueue.put(routeB);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		monitorMap.put("tokunaga", holdBlockingQueue);
		
		RouteEstimate routEstimateLogic = new RouteEstimate(monitorMap);
		routEstimateLogic.estimateRouteForUser();
		
		
	}
	
	public static Timestamp getCurrentTime(){
		Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
		return now;
	}

}
