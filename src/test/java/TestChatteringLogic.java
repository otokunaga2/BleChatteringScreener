import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jp.kobe_u.cs24.service.BleChatteringScreening.XmlParser;
import jp.kobe_u.cs24.service.BleChatteringScreening.logic.ChatteringScreeningLogic;
import jp.kobe_u.cs24.service.BleChatteringScreening.logic.StaticWhenWhereQueue;
import jp.kobe_u.cs24.service.BleChatteringScreening.model.WhenWhere;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */

/**
 * @author tokunaga
 *
 */
public class TestChatteringLogic {

	
	private StaticWhenWhereQueue whenWhereQueue;
	private ChatteringScreeningLogic logic;
	private XmlParser parser;
	private Timestamp beforeTime;
	private Timestamp afterTime;
	private WhenWhere oldWhenWhere;
	private WhenWhere currentWhere;
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
		 whenWhereQueue = StaticWhenWhereQueue.getInstance();
		 logic = new ChatteringScreeningLogic();
		 parser = new XmlParser();
		 oldWhenWhere = new WhenWhere();
		 currentWhere = new WhenWhere();
		 beforeTime = new Timestamp(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse("2015/02/05 21:12:05").getTime());
		 afterTime = new Timestamp(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse("2015/02/05 21:15:05").getTime());
		 
		 oldWhenWhere.setLastUpdate(beforeTime);
		 currentWhere.setLastUpdate(afterTime);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDiffWithPrevDate() {
		String testAPI = "http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=tokunaga";
		WhenWhere currentData = parser.obtainCurrentDataFromWebAPI(testAPI);
		assertEquals(true, logic.diffWithPrevDate(oldWhenWhere, currentWhere));
	}

}
