/**
 * 
 */
package src.test.java;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import jp.kobe_u.cs24.service.BleChatteringScreening.Utility;
import jp.kobe_u.cs24.service.BleChatteringScreening.logic.RouteEstimate;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.Route;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author tokunaga
 *
 */
public class TestRouteEstimate {
	private ArrayBlockingQueue<WhenWhere> holdBlockingQueue;
	private HashMap<String, ArrayBlockingQueue<WhenWhere>> monitorMap;
	private RouteEstimate estimateLogic;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		holdBlockingQueue = new ArrayBlockingQueue<>(2);

		WhenWhere routeA = new WhenWhere("tokunaga", 1, "living",
				Utility.getCurrentTime());
		WhenWhere routeB = new WhenWhere("tokunaga", 1, "entrance",
				Utility.getCurrentTime());
		monitorMap = new HashMap<String, ArrayBlockingQueue<WhenWhere>>();
		try {
			holdBlockingQueue.put(routeA);
			/* put is waiting if the queue is full */
			holdBlockingQueue.put(routeB);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		monitorMap.put("tokunaga", holdBlockingQueue);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link jp.kobe_u.cs24.service.BleChatteringScreening.logic.RouteEstimate#estimateRouteForUser()}
	 * .
	 */
	@Test
	public void testEstimateRouteForUser() {
		estimateLogic = new RouteEstimate(monitorMap);
		Route testRoute = estimateLogic.estimateRouteForUser();
		assertEquals("Outgo", testRoute.getPathName());
		// fail("Not yet implemented");
	
	}

	/**
	 * Test method for
	 * {@link jp.kobe_u.cs24.service.BleChatteringScreening.logic.RouteEstimate#mapStringAsPath(java.lang.String)}
	 * .
	 */
	@Test
	public void testMapStringAsPath() {
		estimateLogic = new RouteEstimate(monitorMap);
		// case "entranceentrance":
		// returnLabel = "WelcomeHome";
		// break;
		// case "entranceliving":
		// returnLabel = "GoLiving";
		// break;
		// case "livingentrance":
		// returnLabel = "Outgo";

		String routeA = estimateLogic.mapStringAsPath("entranceentrance");
		String routeB = estimateLogic.mapStringAsPath("entranceliving");
		String routeC = estimateLogic.mapStringAsPath("livingentrance");
		assertEquals("WelcomeHome", routeA);
		assertEquals("GoLiving", routeB);
		assertEquals("Outgo", routeC);
	}

}
