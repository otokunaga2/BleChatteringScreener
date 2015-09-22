/**
 * 
 */
package src.test.java;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.kobe_u.cs24.service.BleChatteringScreening.XmlParser;
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
public class TestParser {
	private SimpleDateFormat sdf; 
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
		sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link jp.kobe_u.cs24.service.BleChatteringScreening.XmlParser#parseAPI(java.lang.String)}.
	 */
	@Test
	public void testParseAPI() {
		Timestamp parsedDate = XmlParser.parseDate("2015-09-14 18:50:21");
		
		assertNotNull(parsedDate);
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link jp.kobe_u.cs24.service.BleChatteringScreening.XmlParser#parseDate(java.lang.String)}.
	 */
	@Test
	public void testParseDate() {
		Date tempDate = null;
		try {
			tempDate = sdf.parse("2015-09-14 18:50:21");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timestamp testTimedata = new Timestamp(tempDate.getTime());
		WhenWhere testDate = new WhenWhere("tokunaga", 2, "entrance", testTimedata);
		WhenWhere actualData = XmlParser.parseAPI("http://192.168.100.115:8080/LOCS4Beacon/api/whenwhere?userid=tokunaga");
		assertEquals(testDate.getLocationName(), actualData.getLocationName());
	}
	public void testWhenNullInDatabase(){
		
	}

}
